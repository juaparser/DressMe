package com.juaparser.dressme.ui.conjunto.filtrosConjunto

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.enums.Color
import com.juaparser.dressme.database.enums.Tiempo
import com.juaparser.dressme.database.enums.TopCategoria

/*
*
* Adaptador para mostrar la lista de checkbox según enumerado
*
 */

class CheckboxAdapter(
        private val categories: Array<TopCategoria>?,
        private val colors: Array<Color>?,
        private val weather: Array<Tiempo>?
)
    : RecyclerView.Adapter<CheckboxAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_checkbox, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var values = when {
            !categories.isNullOrEmpty() -> categories
            !colors.isNullOrEmpty() -> colors
            else -> weather
        }

        val item = values?.get(position)
        holder.checkbox.text = item?.name

        if(!categories.isNullOrEmpty()) {
            if(DressMeApp.listCheckboxPrendas.contains(item?.name)){
                holder.checkbox.isChecked = true
            }
        }else if (!colors.isNullOrEmpty()) {
            if(DressMeApp.listCheckboxColores.contains(item?.name)){
                holder.checkbox.isChecked = true
            }
        }else if (!weather.isNullOrEmpty()) {
            if(DressMeApp.listCheckboxTiempo.contains(item?.name)){
                holder.checkbox.isChecked = true
            }
        }

        holder.checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                if(!categories.isNullOrEmpty())
                    DressMeApp.listCheckboxPrendas.add(holder.checkbox.text.toString())
                if(!colors.isNullOrEmpty())
                    DressMeApp.listCheckboxColores.add(holder.checkbox.text.toString())
                if(!weather.isNullOrEmpty())
                    DressMeApp.listCheckboxTiempo.add(holder.checkbox.text.toString())
            }
            else {
                if(!categories.isNullOrEmpty())
                    DressMeApp.listCheckboxPrendas.remove(holder.checkbox.text.toString())
                if(!colors.isNullOrEmpty())
                    DressMeApp.listCheckboxColores.remove(holder.checkbox.text.toString())
                if(!weather.isNullOrEmpty())
                    DressMeApp.listCheckboxTiempo.remove(holder.checkbox.text.toString())
            }
        }

    }

    override fun getItemCount(): Int {
        val values = when {
            !categories.isNullOrEmpty() -> categories
            !colors.isNullOrEmpty() -> colors
            else -> weather
        }
        return values!!.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val checkbox: CheckBox = view.findViewById(R.id.checkbox)

    }


}