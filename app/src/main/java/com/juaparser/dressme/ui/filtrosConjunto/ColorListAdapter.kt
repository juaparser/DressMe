package com.juaparser.dressme.ui.filtrosConjunto

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.juaparser.dressme.R


class ColorListAdapter(var ctx: Context, private val values: MutableList<String>, var type: Int)
    : RecyclerView.Adapter<ColorListAdapter.ViewHolder>() {

    private lateinit var view: View


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_color, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = values[position]

        if(type == 0) {
            when (item) {
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

        if(type == 1) {
            val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color, null)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)

            var params = view.layoutParams as RecyclerView.LayoutParams

            params.setMargins(4,0,4,0)
            view.layoutParams = params

            when (item) {
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
            holder.imageView.setImageDrawable(ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color_circle, null))
        }


        if(type == 2) {
            when (item) {
                "Soleado" -> {
                    val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_soleado, null)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                    DrawableCompat.setTint(wrappedDrawable, ResourcesCompat.getColor(ctx.resources, R.color.themeColor, null))
                    holder.imageView.setImageDrawable(wrappedDrawable)
                }
                "Nublado" -> {
                    val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_nublado, null)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                    DrawableCompat.setTint(wrappedDrawable, ResourcesCompat.getColor(ctx.resources, R.color.themeColor, null))
                    holder.imageView.setImageDrawable(wrappedDrawable)
                }
                "Lluvia" -> {
                    val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_lluvia, null)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                    DrawableCompat.setTint(wrappedDrawable, ResourcesCompat.getColor(ctx.resources, R.color.themeColor, null))
                    holder.imageView.setImageDrawable(wrappedDrawable)
                }
                "Nieve" -> {
                    val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_nieve, null)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                    DrawableCompat.setTint(wrappedDrawable, ResourcesCompat.getColor(ctx.resources, R.color.themeColor, null))
                    holder.imageView.setImageDrawable(wrappedDrawable)
                }
                "Claros" -> {
                    val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_claros, null)
                    val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                    DrawableCompat.setTint(wrappedDrawable, ResourcesCompat.getColor(ctx.resources, R.color.themeColor, null))
                    holder.imageView.setImageDrawable(wrappedDrawable)
                }
            }
        }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_color_image)

    }
}