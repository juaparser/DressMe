package com.juaparser.dressme.ui.prenda.armario

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Prenda
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*


class ListAdapter(var ctx: Context, private val values: MutableList<Prenda>)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    private lateinit var parent: ViewGroup

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item, parent, false)

        this.parent = parent

        view.isLongClickable = true

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name
        holder.contentView.text = if(item.subCategory.isNullOrBlank()) "" else item.subCategory
        holder.imageView.setImageURI(item.image)

        if(item.favorite){
            holder.favView.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_fav_fill))
        } else {
            holder.favView.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_fav_empty))
        }

        holder.favView.setOnClickListener {
            if(holder.favView.drawable.constantState == ContextCompat.getDrawable(ctx, R.drawable.ic_fav_empty)?.constantState) {
                doAsync {
                    val dbPrenda = DressMeApp.database.prendaDao().getPrendaById(item.prendaId)

                    val prenda = Prenda(
                            prendaId = dbPrenda.prendaId,
                            name = dbPrenda.name,
                            image = dbPrenda.image,
                            topCategory = dbPrenda.topCategory,
                            subCategory = dbPrenda.subCategory,
                            color = dbPrenda.color,
                            weather = dbPrenda.weather,
                            creationDate = dbPrenda.creationDate,
                            purchaseDate = dbPrenda.purchaseDate,
                            brand = dbPrenda.brand,
                            size = dbPrenda.size,
                            favorite = true
                    )
                    DressMeApp.database.prendaDao().updatePrenda(prenda)

                    uiThread {
                        holder.favView.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_fav_fill))
                        Snackbar.make(parent, "Prenda ${prenda.name} añadida a favoritos.", Snackbar.LENGTH_LONG).show()
                    }
                }
            }else {
                doAsync {
                    val dbPrenda = DressMeApp.database.prendaDao().getPrendaById(item.prendaId)

                    val prenda = Prenda(
                            prendaId = dbPrenda.prendaId,
                            name = dbPrenda.name,
                            image = dbPrenda.image,
                            topCategory = dbPrenda.topCategory,
                            subCategory = dbPrenda.subCategory,
                            color = dbPrenda.color,
                            weather = dbPrenda.weather,
                            creationDate = dbPrenda.creationDate,
                            purchaseDate = dbPrenda.purchaseDate,
                            brand = dbPrenda.brand,
                            size = dbPrenda.size,
                            favorite = false
                    )

                    DressMeApp.database.prendaDao().updatePrenda(prenda)

                    uiThread {
                        holder.favView.setImageDrawable(ContextCompat.getDrawable(ctx, R.drawable.ic_fav_empty))
                        Snackbar.make(parent, "Prenda ${prenda.name} eliminada de favoritos.", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener,
    View.OnClickListener {

        val nameView: TextView = view.findViewById(R.id.item_name)
        val contentView: TextView = view.findViewById(R.id.content)
        val imageView: ImageView = view.findViewById(R.id.itemImage)
        val favView: ImageButton = view.findViewById(R.id.favButton)

        init {
            view.setOnClickListener(this)
            view.setOnCreateContextMenuListener(this)
        }


        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

        override fun onClick(v: View) {
            val item = values[adapterPosition]
            val bundle = Bundle()
            bundle.putInt("itemId",item.prendaId)
            v.findNavController().navigate(R.id.action_nav_armario_to_nav_verPrenda, bundle)
        }

        override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
            val item = values[adapterPosition]
            val edit = menu.add(0, item.prendaId, 0, "Modificar")
            val delete = menu.add(0, item.prendaId, 0, "Eliminar")

            edit.setOnMenuItemClickListener {
                val bundle = Bundle()
                bundle.putBoolean("edit",true)
                bundle.putInt("itemId",item.prendaId)
                v.findNavController().navigate(R.id.action_nav_armario_to_nav_subirRopa, bundle)
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
                                DressMeApp.database.ConjuntoPrendaDao().deleteCrossRefPrenda(item.prendaId.toLong())
                                dao.deletePrenda(item)
                                uiThread {
                                    Snackbar.make(parent, "Prenda ${item.name} eliminada.", Snackbar.LENGTH_LONG)
                                            .setBackgroundTint(ctx.resources.getColor(R.color.reject))
                                            .show()
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