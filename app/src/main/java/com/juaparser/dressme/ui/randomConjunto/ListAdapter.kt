package com.juaparser.dressme.ui.randomConjunto

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
        holder.contentView.text = "${item.brand}, talla ${item.size}"
        holder.imageView.setImageURI(item.image)


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameView: TextView = view.findViewById(R.id.item_name)
        val contentView: TextView = view.findViewById(R.id.content)
        val imageView: ImageView = view.findViewById(R.id.itemImage)

    }


}