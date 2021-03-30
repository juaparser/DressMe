package com.juaparser.dressme.ui.conjunto.filtrosConjunto

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.databinding.FragmentFiltrarConjuntoBinding

/*
*
* Fragmento para los filtros de generar un conjunto aleatorio
*
 */

class FiltrarConjuntoFragment : Fragment() {

    private lateinit var binding: FragmentFiltrarConjuntoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFiltrarConjuntoBinding.inflate(layoutInflater)
        val view = binding.root

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.txtTitle.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }

        binding.layoutColores.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.layoutColores.adapter = ColorListAdapter(
            requireContext(),
            DressMeApp.listCheckboxColores,
            1
        )

        binding.layoutTiempo.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.layoutTiempo.adapter = ColorListAdapter(
            requireContext(),
            DressMeApp.listCheckboxTiempo,
            2
        )

        binding.layoutPrenda.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.layoutPrenda.adapter = ColorListAdapter(
            requireContext(),
            DressMeApp.listCheckboxPrendas,
            0
        )

        binding.root.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_BACK) {
                Toast.makeText(
                    requireContext(),
                    "Debe seleccionar algún tipo de filtrado.",
                    Toast.LENGTH_LONG
                ).show()
                DressMeApp.listCheckboxColores = mutableListOf()
                DressMeApp.listCheckboxTiempo = mutableListOf()
                DressMeApp.listCheckboxPrendas = mutableListOf()
            }
            true
        }

        binding.btnGenerar.setOnClickListener{
            if(DressMeApp.listCheckboxColores.isEmpty() && DressMeApp.listCheckboxPrendas.isEmpty() &&
                    DressMeApp.listCheckboxTiempo.isEmpty()){
                Toast.makeText(
                    requireContext(),
                    "Debe seleccionar algún tipo de filtrado.",
                    Toast.LENGTH_LONG
                ).show()
            } else if (DressMeApp.listCheckboxPrendas.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Debe seleccionar alguna prenda a buscar.",
                    Toast.LENGTH_LONG
                ).show()
            }else {
                findNavController().navigate(R.id.action_nav_filtrarConjunto_to_nav_generarConjunto)
            }
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



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(
                    requireContext(),
                    "Debe seleccionar algún tipo de filtrado.",
                    Toast.LENGTH_LONG
                ).show()
                DressMeApp.listCheckboxColores = mutableListOf()
                DressMeApp.listCheckboxTiempo = mutableListOf()
                DressMeApp.listCheckboxPrendas = mutableListOf()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}