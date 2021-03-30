package com.juaparser.dressme.ui.conjunto.generarConjunto

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.findNavController
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Prenda

/*
*
* Adaptador para mostrar las prendas del conjunto aleatorio
*
 */

class ListAdapter(private val values: MutableList<Prenda>)
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
        holder.contentView.text = if(item.subCategory.isNullOrBlank()) "" else item.subCategory
        holder.imageView.setImageURI(item.image)

        holder.favButton.visibility = View.INVISIBLE



    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameView: TextView = view.findViewById(R.id.item_name)
        val contentView: TextView = view.findViewById(R.id.content)
        val imageView: ImageView = view.findViewById(R.id.itemImage)
        val favButton: ImageButton = view.findViewById(R.id.favButton)

    }


}