package com.juaparser.dressme.ui.inicio

import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.text.Layout
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.databinding.FragmentInicioBinding
import org.jetbrains.anko.doAsync

class InicioFragment : Fragment() {

    private lateinit var binding: FragmentInicioBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentInicioBinding.inflate(layoutInflater)
        val view = binding.root

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.txtWelcomeMessage.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
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