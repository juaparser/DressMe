package com.juaparser.dressme.ui.conjunto.misConjuntos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.databinding.FragmentMisConjuntosBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/*
*
* Fragmento para mostrar los conjuntos del usuario
*
 */

class MisConjuntosFragment : Fragment() {

    private lateinit var binding: FragmentMisConjuntosBinding
    private lateinit var adapter: ConjuntosAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentMisConjuntosBinding.inflate(layoutInflater)
        val view = binding.root

        binding.list.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        binding.buttonSubirConjunto.setOnClickListener { _ ->
            findNavController().navigate(R.id.action_nav_misConjuntos_to_nav_subirConjunto)
        }

        doAsync {
            val conjuntos = DressMeApp.database.conjuntoDao().getAllConjuntos()

            uiThread {
                adapter = ConjuntosAdapter(requireContext(), conjuntos)
                binding.list.adapter = adapter

                if(conjuntos.isNullOrEmpty()){
                    binding.list.visibility = View.INVISIBLE
                    binding.txtEmptyConjuntos.visibility = View.VISIBLE
                }
            }
        }

        return view
    }

}