package com.juaparser.dressme.ui.inicio

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
import com.google.android.material.snackbar.Snackbar
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Conjunto
import com.juaparser.dressme.database.Prenda
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.Serializable


class PrendasRecientesAdapter(var ctx: Context, private val values: MutableList<Prenda>)
    : RecyclerView.Adapter<PrendasRecientesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_item_inicio, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.nameView.text = item.name
        val content = if(item.subCategory == null) item.topCategory.name else item.subCategory
        holder.contentView.text = content
        holder.imagePrenda.setImageURI(item.image)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameView: TextView = view.findViewById(R.id.item_name)
        val contentView: TextView = view.findViewById(R.id.content)
        val imagePrenda: ImageView = view.findViewById(R.id.itemImage)

        init{
            view.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("itemId",values[adapterPosition].prendaId)
                it.findNavController().navigate(R.id.action_nav_home_to_nav_verPrenda, bundle)
            }
        }
    }

}