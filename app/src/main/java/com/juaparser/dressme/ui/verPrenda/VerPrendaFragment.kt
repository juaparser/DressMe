package com.juaparser.dressme.ui.verPrenda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Prenda
import com.juaparser.dressme.databinding.FragmentVerPrendaBinding
import com.juaparser.dressme.ui.armario.ListAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.DateFormat

class VerPrendaFragment : Fragment() {

    lateinit var binding: FragmentVerPrendaBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerPrendaBinding.inflate(layoutInflater)
        val view = binding.root

        loadInfo()

        return view
    }

    fun loadInfo(){
        val prendaId = arguments?.getInt("itemId")

        doAsync {
            val prenda = DressMeApp.database.prendaDao().getPrendaById(prendaId!!)

            uiThread{
                binding.imageRopa.setImageURI(prenda.image)
                binding.textRopaName.text = prenda.name
                binding.textRopaUses.text = prenda.uses.toString()
                binding.textRopaCategoria.text = if(prenda.subCategory?.isNotEmpty() == true) prenda.subCategory else prenda.topCategory.name

                binding.textRopaMarca.text = prenda.brand
                binding.textRopaTalla.text = prenda.size

                val colorAdapter = ColorListAdapter(requireContext(), mutableListOf(prenda.color))
                binding.layoutColores.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                binding.layoutColores.adapter = colorAdapter

                binding.layoutTiempo.adapter = ArrayAdapter(requireContext(), R.layout.menu_item, arrayOf(prenda.weather?.name))

                val dateFormat = DateFormat.getDateInstance()
                if(prenda.purchaseDate == null) {
                    binding.textRopaFechaCompra.text = "Sin información"
                }else binding.textRopaFechaCompra.text = dateFormat.format(prenda.purchaseDate!!)
                binding.textRopaFechaCreacion.text = dateFormat.format(prenda.creationDate)

            }
        }


    }
}