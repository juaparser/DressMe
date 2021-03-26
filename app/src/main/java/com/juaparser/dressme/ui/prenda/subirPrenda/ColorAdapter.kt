package com.juaparser.dressme.ui.prenda.subirPrenda

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.juaparser.dressme.R


class ColorAdapter(context: Context, list: Array<com.juaparser.dressme.database.enums.Color>) :ArrayAdapter<com.juaparser.dressme.database.enums.Color>(
    context,
    0,
    list
) {

    private val mContext: Context = context
    private var colorList: Array<com.juaparser.dressme.database.enums.Color> = list

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem: View? = convertView
        if (listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(
                R.layout.material_color_item,
                parent,
                false
            )

        val currentColor = colorList[position]

        val image: ImageView = listItem?.findViewById(R.id.material_sample_image) as ImageView

        val unwrappedDrawable = ResourcesCompat.getDrawable(context.resources, R.drawable.ic_color, null)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)

       when(currentColor.name) {
           "Rojo" -> DrawableCompat.setTint(wrappedDrawable, Color.RED)
           "Verde" -> DrawableCompat.setTint(wrappedDrawable, Color.GREEN)
           "Amarillo" -> DrawableCompat.setTint(wrappedDrawable, Color.YELLOW)
           "Azul" -> DrawableCompat.setTint(wrappedDrawable, Color.BLUE)
           "Negro" -> DrawableCompat.setTint(wrappedDrawable, Color.BLACK)
           "Blanco" -> DrawableCompat.setTint(wrappedDrawable, Color.WHITE)
           "Naranja" -> DrawableCompat.setTint(wrappedDrawable, context.resources.getColor(R.color.orange))
           "Morado" -> DrawableCompat.setTint(wrappedDrawable, context.resources.getColor(R.color.purple_500))
           "Gris" -> DrawableCompat.setTint(wrappedDrawable, Color.GRAY)
           "Rosa" -> DrawableCompat.setTint(wrappedDrawable, context.resources.getColor(R.color.rose))
       }

        image.setImageDrawable(ResourcesCompat.getDrawable(context.resources, R.drawable.ic_color_circle, null))

        val name = listItem.findViewById(R.id.material_sample_text) as TextView
        name.text = currentColor.name

        return listItem
    }


}
