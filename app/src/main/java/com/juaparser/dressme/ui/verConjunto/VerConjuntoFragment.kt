package com.juaparser.dressme.ui.verConjunto

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        setHasOptionsMenu(true)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.view_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val conjuntoId = arguments?.getInt("itemId")
        when(item.itemId) {
            R.id.editItem -> {
                val bundle = Bundle()
                bundle.putBoolean("edit",true)
                bundle.putInt("itemId",conjuntoId!!)
                findNavController().navigate(R.id.action_nav_misConjuntos_to_nav_verConjunto, bundle)
            }
            R.id.deleteItem -> {
                val dialog = MaterialAlertDialogBuilder(requireContext())
                        .setTitle("¿Eliminar conjunto?")
                        .setMessage("No se eliminarán las prendas que se hayan asignado.")
                        .setNeutralButton("Cancelar") { dialog, which ->
                            // Respond to neutral button press
                        }
                        .setPositiveButton("Eliminar") { dialog, which ->
                            doAsync{
                                val conjunto = DressMeApp.database.conjuntoDao().getConjuntoById(conjuntoId!!.toLong())
                                val list = DressMeApp.database.ConjuntoPrendaDao().getAllCrossRef(conjunto.conjuntoId)
                                DressMeApp.database.ConjuntoPrendaDao().deletePrendasConConjunto(list)

                                DressMeApp.database.conjuntoDao().deleteConjunto(conjunto)
                                uiThread {
                                    findNavController().navigate(R.id.action_nav_verConjunto_to_nav_misConjuntos)
                                }
                            }
                        }
                dialog.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}