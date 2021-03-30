package com.juaparser.dressme.ui.conjunto.verConjunto

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Prenda
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/*
*
* Adaptador para mostrar las prendas asociadas al conjunto
*
 */

class PrendasConjuntoAdapter(var ctx: Context, var conjuntoId: Long, private val values: MutableList<Prenda>)
    : RecyclerView.Adapter<PrendasConjuntoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item_ropa, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name
        val content = if(item.subCategory == null) item.topCategory.name else item.subCategory
        holder.contentView.text = content
        holder.imagePrenda.setImageURI(item.image)

        if(values.size < 2 ) {
            holder.quitarPrenda.visibility = View.INVISIBLE
            //notifyDataSetChanged()
        } else {
            holder.quitarPrenda.visibility = View.VISIBLE
            //notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imagePrenda: ImageView = view.findViewById(R.id.imagePrenda)
        val nameView: TextView = view.findViewById(R.id.item_name)
        val contentView: TextView = view.findViewById(R.id.content)
        val quitarPrenda: ImageView = view.findViewById(R.id.btn_quitarPrenda)


        init {
            view.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("itemId",values[adapterPosition].prendaId)
                it.findNavController().navigate(R.id.action_nav_verConjunto_to_nav_verPrenda, bundle)
            }

            quitarPrenda.setOnClickListener {
                if(values.size < 2 ) {
                    Toast.makeText(ctx, "El conjunto no puede tener 0 prendas.", Toast.LENGTH_LONG).show()
                } else {
                    val prenda = values[adapterPosition]
                    doAsync {
                        DressMeApp.database.ConjuntoPrendaDao().deleteCrossRef(conjuntoId, prenda.prendaId.toLong())
                        uiThread {
                            val pos = values.indexOf(prenda)
                            values.remove(prenda)
                            this@PrendasConjuntoAdapter.notifyItemRemoved(pos)
                            Snackbar.make(view, "Eliminada prenda ${prenda.name} del conjunto.", Snackbar.LENGTH_LONG).show()
                        }
                    }
                }

            }
        }

    }

}