package com.juaparser.dressme.ui.verConjunto

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
import com.juaparser.dressme.database.Conjunto
import com.juaparser.dressme.database.Prenda
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.Serializable


class PrendasConjuntoAdapter(var ctx: Context, private val values: List<Prenda>)
    : RecyclerView.Adapter<PrendasConjuntoAdapter.ViewHolder>() {

    private lateinit var item: Prenda

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item_ropa, parent, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        item = values[position]
        holder.nameView.text = item.name
        holder.contentView.text = item.uses.toString()
        holder.imagePrenda.setImageURI(item.image)


    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imagePrenda: ImageView = view.findViewById(R.id.imagePrenda)
        val nameView: TextView = view.findViewById(R.id.item_name)
        val contentView: TextView = view.findViewById(R.id.content)

        init {
            view.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("itemId",item.prendaId)
                it.findNavController().navigate(R.id.nav_verPrenda, bundle)
            }
        }

    }

}