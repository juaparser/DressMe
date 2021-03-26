package com.juaparser.dressme.ui.inicio

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.databinding.FragmentInicioBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class InicioFragment : Fragment() {

    private lateinit var binding: FragmentInicioBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentInicioBinding.inflate(layoutInflater)
        val view = binding.root

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.txtWelcomeMessage.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }

        doAsync {
            if(!DressMeApp.database.prendaDao().getAllPrendas().isNullOrEmpty()){
                val prendas = DressMeApp.database.prendaDao().getPrendasDate()
                uiThread {
                    binding.prendasList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    if(prendas.size > 3)
                        binding.prendasList.adapter = PrendasRecientesAdapter(requireContext(), prendas.subList(0,2))
                    else binding.prendasList.adapter = PrendasRecientesAdapter(requireContext(), prendas)
                }
            } else {
                uiThread {
                    binding.layoutRecientes.visibility = View.INVISIBLE
                }
            }
        }

        binding.btnGenerarConjunto.setOnClickListener {
            if(binding.btnGenerarConjunto.text == getString(R.string.btn_primera_prenda))
                findNavController().navigate(R.id.action_nav_home_to_nav_subirRopa)
            else
                findNavController().navigate(R.id.action_nav_home_to_nav_filtrarConjunto)
        }

        doAsync {
            val prendas = DressMeApp.database.prendaDao().getAllPrendas()
            if(prendas.isNullOrEmpty()){
                binding.txtGenerarConjunto.text = getString(R.string.primera_prenda)
                binding.btnGenerarConjunto.text = getString(R.string.btn_primera_prenda)
            }
        }

        return view
    }

}