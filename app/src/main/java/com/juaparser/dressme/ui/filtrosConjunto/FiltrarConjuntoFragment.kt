package com.juaparser.dressme.ui.filtrosConjunto

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.databinding.FragmentFiltrarConjuntoBinding


class FiltrarConjuntoFragment : Fragment() {

    private lateinit var binding: FragmentFiltrarConjuntoBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentFiltrarConjuntoBinding.inflate(layoutInflater)
        val view = binding.root

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.txtTitle.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }

        binding.layoutColores.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.layoutColores.adapter = ColorListAdapter(requireContext(), DressMeApp.listCheckboxColores, 1)

        binding.layoutTiempo.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.layoutTiempo.adapter = ColorListAdapter(requireContext(), DressMeApp.listCheckboxTiempo, 2)

        binding.layoutPrenda.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.layoutPrenda.adapter = ColorListAdapter(requireContext(), DressMeApp.listCheckboxPrendas, 0)

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                DressMeApp.listCheckboxColores = mutableListOf()
                DressMeApp.listCheckboxTiempo = mutableListOf()
                DressMeApp.listCheckboxPrendas = mutableListOf()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.btnGenerar.setOnClickListener{

            findNavController().navigate(R.id.nav_generarConjunto)
        }

        if(DressMeApp.listCheckboxColores.isNotEmpty()){
            binding.layoutColores.visibility = View.VISIBLE
        }
        if(DressMeApp.listCheckboxTiempo.isNotEmpty()){
            binding.layoutTiempo.visibility = View.VISIBLE
        }
        if(DressMeApp.listCheckboxPrendas.isNotEmpty()){
            binding.layoutPrenda.visibility = View.VISIBLE
        }

        binding.btnPrenda.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("type", 0)
            findNavController().navigate(R.id.nav_checkboxFragment, bundle)
        }

        binding.btnColores.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("type", 1)
            findNavController().navigate(R.id.nav_checkboxFragment, bundle)
        }

        binding.btnTiempo.setOnClickListener{
            var bundle = Bundle()
            bundle.putInt("type", 2)
            findNavController().navigate(R.id.nav_checkboxFragment, bundle)
        }

        return view
    }


}