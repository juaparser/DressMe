package com.juaparser.dressme.ui.armario

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Prenda
import com.juaparser.dressme.database.TopCategoria
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ItemFragment(var i: Int) : Fragment() {

    private var columnCount = 2
    private lateinit var prendasList: MutableList<Prenda>
    private lateinit var adapter: ListAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        val recview: RecyclerView = view.findViewById(R.id.list)

        recview.layoutManager = GridLayoutManager(context, columnCount)

        doAsync {
            prendasList = when(i) {
                0 -> DressMeApp.database.prendaDao().getPrendasByCategory(TopCategoria.Accesorios)
                1 -> DressMeApp.database.prendaDao().getPrendasByCategory(TopCategoria.Superior)
                2 -> DressMeApp.database.prendaDao().getPrendasByCategory(TopCategoria.Inferior)
                else -> DressMeApp.database.prendaDao().getPrendasByCategory(TopCategoria.Zapatos)
            }
            uiThread {
                adapter = ListAdapter(requireContext(), prendasList)
                recview.adapter = adapter
            }
        }

        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
        val menuAdapter = ArrayAdapter(requireContext(), R.layout.menu_item, items)
        val textField : AutoCompleteTextView = view.findViewById(R.id.textField)
        textField.setAdapter(menuAdapter)

        return view
    }

}