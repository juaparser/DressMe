package com.juaparser.dressme.ui.conjunto.generarConjunto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.enums.Color
import com.juaparser.dressme.database.Prenda
import com.juaparser.dressme.database.enums.Tiempo
import com.juaparser.dressme.database.enums.TopCategoria
import com.juaparser.dressme.databinding.FragmentGenerarConjuntoBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class GenerarConjuntoFragment : Fragment() {

    private lateinit var binding: FragmentGenerarConjuntoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGenerarConjuntoBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnVolver.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnReintentar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnCrear.setOnClickListener {
            findNavController().navigate(R.id.action_nav_generarConjunto_to_nav_subirConjunto)
        }


        val colorList: MutableList<Color> = mutableListOf()
        val weatherList: MutableList<Tiempo> = mutableListOf()

        if(!DressMeApp.listCheckboxColores.isNullOrEmpty()) {
            for (c in DressMeApp.listCheckboxColores) {
                for (d in Color.values()) {
                    if (c == d.name) {
                        colorList.add(d)
                    }
                }
            }
        }
        if(!DressMeApp.listCheckboxTiempo.isNullOrEmpty()) {
            for (c in DressMeApp.listCheckboxTiempo) {
                for (d in Tiempo.values()) {
                    if (c == d.name) {
                        weatherList.add(d)
                    }
                }
            }
        }
        binding.list.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        doAsync {
            val prendas = DressMeApp.listCheckboxPrendas
            var accesorio: MutableList<Prenda>? = null
            var superior: MutableList<Prenda>? = null
            var inferior: MutableList<Prenda>? = null
            var zapatos: MutableList<Prenda>? = null
            for (p in prendas) {
                if (colorList.isEmpty() && weatherList.isEmpty()) {
                    if (p == TopCategoria.Accesorio.name) {
                        accesorio = DressMeApp.database.prendaDao()
                            .getPrendasByCategory(TopCategoria.Accesorio)
                    }
                    if (p == TopCategoria.Superior.name) {
                        superior = DressMeApp.database.prendaDao()
                            .getPrendasByCategory(TopCategoria.Superior)
                    }
                    if (p == TopCategoria.Inferior.name) {
                        inferior = DressMeApp.database.prendaDao()
                            .getPrendasByCategory(TopCategoria.Inferior)
                    }
                    if (p == TopCategoria.Calzado.name) {
                        zapatos = DressMeApp.database.prendaDao()
                            .getPrendasByCategory(TopCategoria.Calzado)
                    }
                } else {
                    if (p == TopCategoria.Accesorio.name) {
                        accesorio = DressMeApp.database.prendaDao()
                            .getPrendasByFilters(TopCategoria.Accesorio, colorList, weatherList)
                    }
                    if (p == TopCategoria.Superior.name) {
                        superior = DressMeApp.database.prendaDao()
                            .getPrendasByFilters(TopCategoria.Superior, colorList, weatherList)
                    }
                    if (p == TopCategoria.Inferior.name) {
                        inferior = DressMeApp.database.prendaDao()
                            .getPrendasByFilters(TopCategoria.Inferior, colorList, weatherList)
                    }
                    if (p == TopCategoria.Calzado.name) {
                        zapatos = DressMeApp.database.prendaDao()
                            .getPrendasByFilters(TopCategoria.Calzado, colorList, weatherList)
                    }
                }
            }
            uiThread {
                val prendasUi = mutableListOf<Prenda>()
                if(!accesorio.isNullOrEmpty()){
                    prendasUi.add(accesorio.random())
                }
                if(!superior.isNullOrEmpty()){
                    prendasUi.add(superior.random())
                }
                if(!inferior.isNullOrEmpty()){
                    prendasUi.add(inferior.random())
                }
                if(!zapatos.isNullOrEmpty()){
                    prendasUi.add(zapatos.random())
                }
                DressMeApp.listPrendas = prendasUi
                binding.list.adapter = ListAdapter(prendasUi)

                if(prendasUi.isEmpty()) {
                    binding.layoutSinPrendas.visibility = View.VISIBLE
                } else {
                    binding.layoutConPrendas.visibility = View.VISIBLE
                }
            }
        }

        return view
    }

}