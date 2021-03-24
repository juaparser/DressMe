package com.juaparser.dressme.ui.misConjuntos

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Color
import com.juaparser.dressme.database.Prenda


class PrendaConjuntoAdapter(var ctx: Context, private val values: MutableList<Prenda>)
    : RecyclerView.Adapter<PrendaConjuntoAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_color, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = values[position]

        when (item.topCategory.name) {
                "Accesorio" -> {
                    val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_reloj, null)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                    DrawableCompat.setTint(wrappedDrawable, ResourcesCompat.getColor(ctx.resources, R.color.themeColor, null))
                    holder.imageView.setImageDrawable(wrappedDrawable)
                }
                "Superior" -> {
                    val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_camiseta, null)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                    DrawableCompat.setTint(wrappedDrawable, ResourcesCompat.getColor(ctx.resources, R.color.themeColor, null))
                    holder.imageView.setImageDrawable(wrappedDrawable)
                }
                "Inferior" -> {
                    val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_pantalon, null)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                    DrawableCompat.setTint(wrappedDrawable, ResourcesCompat.getColor(ctx.resources, R.color.themeColor, null))
                    holder.imageView.setImageDrawable(wrappedDrawable)
                }
                "Calzado" -> {
                    val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_zapatos, null)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                    DrawableCompat.setTint(wrappedDrawable, ResourcesCompat.getColor(ctx.resources, R.color.themeColor, null))
                    holder.imageView.setImageDrawable(wrappedDrawable)
                }
            }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_color_image)

    }
}