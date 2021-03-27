package com.juaparser.dressme.onboarding

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.juaparser.dressme.MainActivity
import com.juaparser.dressme.R

/*
    Adaptador para la creación del tutorial con vistas deslizantes usando PagerAdapter
 */

class SlideViewPagerAdapter(var ctx: Context, var viewPager: ViewPager) : PagerAdapter() {
    override fun getCount(): Int {
        return 4
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.slide_screen, container, false)

        val ind1 = view.findViewById<ImageView>(R.id.ind1)
        val ind2 = view.findViewById<ImageView>(R.id.ind2)
        val ind3 = view.findViewById<ImageView>(R.id.ind3)
        val ind4 = view.findViewById<ImageView>(R.id.ind4)

        val btnGetStarted = view.findViewById<Button>(R.id.btnGetStarted)

        btnGetStarted.setBackgroundColor(R.attr.colorPrimary)

        btnGetStarted.setOnClickListener {
            val intent = Intent(ctx, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            ctx.startActivity(intent)
        }

        val unwrappedDrawable = ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color, null)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, ctx.resources.getColor(R.color.brown))


        when (position) {
            0 -> {
                val layout = view.findViewById<LinearLayout>(R.id.layout_welcome)
                layout.visibility = View.VISIBLE

                ind1.setImageDrawable(ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color_circle, null))
            }
            1 -> {
                val layout = view.findViewById<LinearLayout>(R.id.layout_welcome)
                val layout2 = view.findViewById<LinearLayout>(R.id.layout_armario)

                layout.visibility = View.INVISIBLE
                layout2.visibility = View.VISIBLE

                ind1.setImageDrawable(ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color_circle2, null))
                ind2.setImageDrawable(wrappedDrawable)

            }
            2 -> {
                val layout = view.findViewById<LinearLayout>(R.id.layout_armario)
                val layout2 = view.findViewById<LinearLayout>(R.id.layout_conjuntos)

                layout.visibility = View.INVISIBLE
                layout2.visibility = View.VISIBLE

                ind2.setImageDrawable(ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color_circle2, null))
                ind3.setImageDrawable(ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color_circle, null))
            }
            3 -> {
                val layout = view.findViewById<LinearLayout>(R.id.layout_conjuntos)
                val layout2 = view.findViewById<LinearLayout>(R.id.layout_generar)

                layout.visibility = View.INVISIBLE
                layout2.visibility = View.VISIBLE

                ind3.setImageDrawable(ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color_circle2, null))
                ind4.setImageDrawable(ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_color_circle, null))
                btnGetStarted.text = "Empezar"
         }

        }
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}
