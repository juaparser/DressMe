package com.juaparser.dressme.ui.armario

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageButton
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Prenda
import com.juaparser.dressme.database.TopCategoria
import com.juaparser.dressme.database.enum.Accesorio
import com.juaparser.dressme.database.enum.Calzado
import com.juaparser.dressme.database.enum.Inferior
import com.juaparser.dressme.database.enum.Superior
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

        val button: ImageButton = view.findViewById(R.id.cancelFilter)

        val items = when(i) {
            0 -> Accesorio.values()
            1 -> Superior.values()
            2 -> Inferior.values()
            else -> Calzado.values()
        }

        doAsync {
            prendasList = if(DressMeApp.favPrendas){
                when(i) {
                    0 -> DressMeApp.database.prendaDao().getFavoritesPrendasByCategory(TopCategoria.Accesorio)
                    1 -> DressMeApp.database.prendaDao().getFavoritesPrendasByCategory(TopCategoria.Superior)
                    2 -> DressMeApp.database.prendaDao().getFavoritesPrendasByCategory(TopCategoria.Inferior)
                    else -> DressMeApp.database.prendaDao().getFavoritesPrendasByCategory(TopCategoria.Calzado)
                }
            } else {
                when (i) {
                    0 -> DressMeApp.database.prendaDao().getPrendasByCategory(TopCategoria.Accesorio)
                    1 -> DressMeApp.database.prendaDao().getPrendasByCategory(TopCategoria.Superior)
                    2 -> DressMeApp.database.prendaDao().getPrendasByCategory(TopCategoria.Inferior)
                    else -> DressMeApp.database.prendaDao().getPrendasByCategory(TopCategoria.Calzado)
                }
            }

            uiThread {
                adapter = ListAdapter(requireContext(), prendasList)
                recview.adapter = adapter
            }
        }

        val menuAdapter = ArrayAdapter(requireContext(), R.layout.menu_item, items)
        val textField : AutoCompleteTextView = view.findViewById(R.id.textField)
        textField.setAdapter(menuAdapter)

        button.setOnClickListener {
            textField.setText("")
            adapter = ListAdapter(requireContext(), prendasList)
            recview.adapter = adapter
            it.visibility = View.INVISIBLE
        }

        textField.setOnItemClickListener { parent, view1, position, id ->
            button.visibility = View.VISIBLE
            var onon = items[position]
            doAsync {
                val newList = DressMeApp.database.prendaDao().getPrendasBySubcategories(onon.name)
                uiThread {
                    adapter = ListAdapter(requireContext(), newList)
                    recview.adapter = adapter
                }
            }

        }

        return view
    }

}