package com.juaparser.dressme.ui.armario

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Prenda
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.Serializable


class ListAdapter(var ctx: Context, private val values: MutableList<Prenda>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private lateinit var item: Prenda

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)

        view.isLongClickable = true

        view.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("itemId",item.prendaId)
            it.findNavController().navigate(R.id.action_nav_armario_to_nav_verPrenda, bundle)
        }


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        item = values[position]
        holder.nameView.text = item.name
        holder.contentView.text = item.subCategory
        holder.imageView.setImageURI(item.image)


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener {

        val nameView: TextView = view.findViewById(R.id.item_name)
        val contentView: TextView = view.findViewById(R.id.content)
        val imageView: ImageView = view.findViewById(R.id.itemImage)

        init {
            view.setOnCreateContextMenuListener(this)
        }


        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
            val edit = menu.add(0, item.prendaId, 0, "Modificar")
            val delete = menu.add(0, item.prendaId, 0, "Eliminar")

            edit.setOnMenuItemClickListener {
                val bundle = Bundle()
                bundle.putBoolean("edit",true)
                bundle.putInt("itemId",item.prendaId)
                v.findNavController().navigate(R.id.nav_subirRopa, bundle)
                true
            }

            delete.setOnMenuItemClickListener {
                val dialog = MaterialAlertDialogBuilder(ctx)
                        .setTitle("¿Eliminar prenda?")
                        .setMessage("Se eliminará la prenda de tu armario, así como de los conjuntos" +
                                " que hayas guardado.")
                        .setNeutralButton("Cancelar") { dialog, which ->
                            // Respond to neutral button press
                        }
                        .setPositiveButton("Eliminar") { dialog, which ->
                            doAsync{
                                val dao = DressMeApp.database.prendaDao()
                                dao.deletePrenda(item)
                                uiThread {
                                    values.remove(item)
                                    this@ListAdapter.notifyItemRemoved(values.indexOf(item))
                                }
                            }
                        }
                dialog.show()
                true
            }
        }



    }


}