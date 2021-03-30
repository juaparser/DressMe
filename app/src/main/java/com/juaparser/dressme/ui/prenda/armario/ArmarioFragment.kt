package com.juaparser.dressme.ui.prenda.armario

import android.content.Context
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.databinding.FragmentArmarioBinding

/*
*
* Fragmento para mostrar las prendas del usuario por categorías
*
 */

class ArmarioFragment : Fragment() {

    private lateinit var demoCollectionPagerAdapter: DemoCollectionPagerAdapter
    private lateinit var viewPager: ViewPager
    private lateinit var binding: FragmentArmarioBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentArmarioBinding.inflate(layoutInflater)
        val view = binding.root

        binding.buttonSubirPrenda.setOnClickListener { _ ->
            findNavController().navigate(R.id.action_nav_armario_to_nav_subirRopa)
        }

        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        demoCollectionPagerAdapter = DemoCollectionPagerAdapter(childFragmentManager, requireContext())
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = demoCollectionPagerAdapter

        val tabLayout: TabLayout = view.findViewById(R.id.tab_layout)
        tabLayout.setupWithViewPager(viewPager)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val favItem = menu.findItem(R.id.favorites)
        favItem.isVisible = true
        if(DressMeApp.favPrendas) {
            favItem.setIcon(R.drawable.ic_fav_fill)
        }
        else {
            favItem.setIcon(R.drawable.ic_fav_empty)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.favorites -> {
                if(item.icon.constantState == ContextCompat.getDrawable(requireContext(), R.drawable.ic_fav_empty)?.constantState) {
                    item.setIcon(R.drawable.ic_fav_fill)
                    DressMeApp.favPrendas = true
                    demoCollectionPagerAdapter = DemoCollectionPagerAdapter(childFragmentManager, requireContext())
                    viewPager.adapter = demoCollectionPagerAdapter
                }
                else {
                    item.setIcon(R.drawable.ic_fav_empty)
                    DressMeApp.favPrendas = false
                    demoCollectionPagerAdapter = DemoCollectionPagerAdapter(childFragmentManager, requireContext())
                    viewPager.adapter = demoCollectionPagerAdapter
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

}

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
class DemoCollectionPagerAdapter(fm: FragmentManager, var ctx: Context) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int  = 4

    override fun getItem(i: Int): Fragment {
        return ItemFragment(i)
    }

    override fun getPageTitle(position: Int): CharSequence {

        val icon = when(position){
            0 -> ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_reloj,null)
            1 ->  ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_camiseta,null)
            2 -> ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_pantalon,null)
            3 -> ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_zapatos,null)
            else -> ResourcesCompat.getDrawable(ctx.resources, R.drawable.ic_armario,null)
        }

        val sb = SpannableStringBuilder("   ") // space added before text for convenience

        try {
            icon?.setBounds(5, 5, icon.intrinsicWidth, icon.intrinsicHeight)
            val span = ImageSpan(icon!!, DynamicDrawableSpan.ALIGN_BASELINE)
            sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        } catch (e: Exception) {
            // TODO: handle exception
        }

        return sb
    }
}

