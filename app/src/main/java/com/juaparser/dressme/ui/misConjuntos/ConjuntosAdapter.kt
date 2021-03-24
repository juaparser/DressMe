package com.juaparser.dressme.ui.misConjuntos

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Conjunto
import com.juaparser.dressme.database.Prenda
import com.juaparser.dressme.ui.filtrosConjunto.ColorListAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.Serializable


class ConjuntosAdapter(var ctx: Context, private val values: MutableList<Conjunto>)
    : RecyclerView.Adapter<ConjuntosAdapter.ViewHolder>() {

    private lateinit var prendasList: MutableList<Prenda>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item_conjunto, parent, false)

        view.isLongClickable = true



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name
        holder.imageView.setImageURI(item.image)
        doAsync {
            val prendas = DressMeApp.database.ConjuntoPrendaDao().getConjuntoConPrendas(item.conjuntoId)
            uiThread {
                holder.contentView.layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.HORIZONTAL, false)
                holder.contentView.adapter = PrendaConjuntoAdapter(ctx, prendas.prendas)

            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener,
    View.OnClickListener {

        val nameView: TextView = view.findViewById(R.id.item_name)
        val contentView: RecyclerView = view.findViewById(R.id.content)
        val imageView: ImageView = view.findViewById(R.id.iv_conjunto)

        init {
            view.setOnClickListener(this)
            view.setOnCreateContextMenuListener(this)
        }


        override fun onClick(v: View) {
            val item = values[adapterPosition]
            val bundle = Bundle()
            bundle.putLong("itemId",item.conjuntoId)
            v.findNavController().navigate(R.id.nav_verConjunto, bundle)
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
            val item = values[adapterPosition]
            val edit = menu.add(0, item.conjuntoId.toInt(), 0, "Modificar")
            val delete = menu.add(0, item.conjuntoId.toInt(), 0, "Eliminar")

            edit.setOnMenuItemClickListener {
                val bundle = Bundle()
                bundle.putBoolean("edit",true)
                bundle.putLong("itemId",item.conjuntoId)
                v.findNavController().navigate(R.id.action_nav_misConjuntos_to_nav_subirConjunto, bundle)
                true
            }

            delete.setOnMenuItemClickListener {
                val dialog = MaterialAlertDialogBuilder(ctx)
                        .setTitle("¿Eliminar conjunto?")
                        .setMessage("No se eliminarán las prendas que se hayan asignado.")
                        .setNeutralButton("Cancelar") { dialog, which ->
                            // Respond to neutral button press
                        }
                        .setPositiveButton("Eliminar") { dialog, which ->
                            doAsync{
                                val list = DressMeApp.database.ConjuntoPrendaDao().getAllCrossRef(item.conjuntoId)

                                DressMeApp.database.ConjuntoPrendaDao().deletePrendasConConjunto(list)

                                DressMeApp.database.conjuntoDao().deleteConjunto(item)
                                uiThread {
                                    values.remove(item)
                                    this@ConjuntosAdapter.notifyItemRemoved(values.indexOf(item))
                                }
                            }
                        }
                dialog.show()
                true
            }
        }



    }


}