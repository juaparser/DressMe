package com.juaparser.dressme.ui.filtrosConjunto

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.Color
import com.juaparser.dressme.database.Tiempo
import com.juaparser.dressme.database.TopCategoria
import com.juaparser.dressme.databinding.FragmentCheckboxBinding


class CheckboxFragment : Fragment() {

    private lateinit var binding: FragmentCheckboxBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCheckboxBinding.inflate(layoutInflater)
        val view = binding.root

        var type = arguments?.getInt("type")

        binding.btnConfirmarColores.setOnClickListener {
            findNavController().navigateUp()
        }


        when(type) {
            0 -> {
                binding.listCheckbox.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.listCheckbox.adapter = CheckboxAdapter(TopCategoria.values(), null, null)
                binding.btnConfirmarColores.text = requireContext().resources.getString(R.string.confirmar_categorias)
            }
            1 ->{
                binding.listCheckbox.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.listCheckbox.adapter = CheckboxAdapter(null, Color.values(), null)

            }
            2 -> {
                binding.listCheckbox.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                binding.listCheckbox.adapter = CheckboxAdapter(null, null, Tiempo.values())
                binding.btnConfirmarColores.text = requireContext().resources.getString(R.string.confirmar_tiempo)
            }
        }

        return view
    }

}