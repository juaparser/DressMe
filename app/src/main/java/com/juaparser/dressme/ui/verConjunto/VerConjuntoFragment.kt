package com.juaparser.dressme.ui.verConjunto

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.databinding.FragmentVerConjuntoBinding
import com.juaparser.dressme.ui.verPrenda.ColorListAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.DateFormat


class VerConjuntoFragment : Fragment() {

    private lateinit var binding: FragmentVerConjuntoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentVerConjuntoBinding.inflate(layoutInflater)
        val view = binding.root

        loadInfo()

        return view
    }

    fun loadInfo(){
        val conjuntoId = arguments?.getLong("itemId")

        doAsync {
            val conjunto = DressMeApp.database.conjuntoDao().getConjuntoById(conjuntoId!!)
            val prendas = DressMeApp.database.ConjuntoPrendaDao().getConjuntoConPrendas(conjunto.conjuntoId).prendas

            uiThread{
                binding.imageConjunto.setImageURI(conjunto.image)
                binding.textConjuntoName.text = conjunto.name
                binding.textConjuntoUses.text = conjunto.uses.toString()

                binding.layoutTiempo.adapter = ArrayAdapter(requireContext(), R.layout.menu_item, arrayOf(conjunto.weather?.name))

                binding.prendasConjuntoList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.prendasConjuntoList.adapter = PrendasConjuntoAdapter(requireContext(),conjuntoId, prendas)

            }
        }


    }

}