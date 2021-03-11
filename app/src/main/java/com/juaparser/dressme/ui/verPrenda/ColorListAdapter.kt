package com.juaparser.dressme.ui.verPrenda

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


class ColorListAdapter(var ctx: Context, private val values: MutableList<Color>)
    : RecyclerView.Adapter<ColorListAdapter.ViewHolder>() {

    private lateinit var item: Color

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_color, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        item = values[position]
        val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color, null)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)

        when(item.name) {
            "Rojo" -> DrawableCompat.setTint(wrappedDrawable, android.graphics.Color.RED)
            "Verde" -> DrawableCompat.setTint(wrappedDrawable, android.graphics.Color.GREEN)
            "Amarillo" -> DrawableCompat.setTint(wrappedDrawable, android.graphics.Color.YELLOW)
            "Azul" -> DrawableCompat.setTint(wrappedDrawable, android.graphics.Color.BLUE)
            "Negro" -> DrawableCompat.setTint(wrappedDrawable, android.graphics.Color.BLACK)
            "Blanco" -> DrawableCompat.setTint(wrappedDrawable, android.graphics.Color.WHITE)
            "Naranja" -> DrawableCompat.setTint(wrappedDrawable, ctx.resources.getColor(R.color.orange))
            "Morado" -> DrawableCompat.setTint(wrappedDrawable, ctx.resources.getColor(R.color.purple_500))
            "Gris" -> DrawableCompat.setTint(wrappedDrawable, android.graphics.Color.GRAY)
            "Rosa" -> DrawableCompat.setTint(wrappedDrawable, ctx.resources.getColor(R.color.rose))
        }

        holder.imageView.setImageDrawable(wrappedDrawable)

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_color_image)

    }
}