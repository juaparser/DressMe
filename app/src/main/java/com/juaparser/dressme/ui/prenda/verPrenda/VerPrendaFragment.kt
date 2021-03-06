package com.juaparser.dressme.ui.prenda.verPrenda

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.databinding.FragmentVerPrendaBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.DateFormat

/*
*
* Fragmento para ver más detalles de una prenda
*
 */

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
        setHasOptionsMenu(true)
        return view
    }

    fun loadInfo(){
        val prendaId = arguments?.getInt("itemId")

        doAsync {
            val prenda = DressMeApp.database.prendaDao().getPrendaById(prendaId!!)

            uiThread{
                binding.imageRopa.setImageURI(prenda.image)
                binding.textRopaName.text = prenda.name
                binding.textRopaCategoria.text = if(prenda.subCategory?.isNotEmpty() == true) prenda.subCategory else prenda.topCategory.name

                binding.textRopaMarca.text = if(!prenda.brand.isNullOrBlank()) prenda.brand else "Sin definir"
                binding.textRopaTalla.text = if(!prenda.size.isNullOrBlank()) prenda.brand else "Sin definir"

                val colorAdapter = ColorListAdapter(requireContext(), mutableListOf(prenda.color))
                binding.layoutColores.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                binding.layoutColores.adapter = colorAdapter

                if (prenda.weather != null)
                    binding.layoutTiempo.adapter = ArrayAdapter(requireContext(), R.layout.menu_item, arrayOf(prenda.weather?.name))
                else
                    binding.layoutTiempo.adapter = ArrayAdapter(requireContext(), R.layout.menu_item, arrayOf("Sin definir"))

                val dateFormat = DateFormat.getDateInstance()
                if(prenda.purchaseDate == null) {
                    binding.textRopaFechaCompra.text = "Sin definir"
                }else binding.textRopaFechaCompra.text = dateFormat.format(prenda.purchaseDate!!)
                binding.textRopaFechaCreacion.text = dateFormat.format(prenda.creationDate)

            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.view_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val prendaId = arguments?.getInt("itemId")
        when(item.itemId) {
            R.id.editItem -> {
                val bundle = Bundle()
                bundle.putBoolean("edit",true)
                bundle.putInt("itemId",prendaId!!)
                findNavController().navigate(R.id.action_nav_verPrenda_to_nav_subirRopa, bundle)
            }
            R.id.deleteItem -> {
                val dialog = MaterialAlertDialogBuilder(requireContext())
                        .setTitle("¿Eliminar prenda?")
                        .setMessage("Se eliminará la prenda de tu armario, así como de los conjuntos" +
                                " que hayas guardado.")
                        .setNeutralButton("Cancelar") { dialog, which ->
                            // Respond to neutral button press
                        }
                        .setPositiveButton("Eliminar") { dialog, which ->
                            doAsync{
                                val dao = DressMeApp.database.prendaDao()
                                val prenda = dao.getPrendaById(prendaId!!)
                                DressMeApp.database.ConjuntoPrendaDao().deleteCrossRefPrenda(prendaId!!.toLong())
                                dao.deletePrenda(prenda)
                                uiThread {
                                    findNavController().navigate(R.id.action_nav_verPrenda_to_nav_armario)
                                    Snackbar.make(binding.root, "Prenda ${prenda.name} eliminada.", Snackbar.LENGTH_LONG)
                                            .setBackgroundTint(resources.getColor(R.color.reject))
                                            .show()
                                }
                            }
                        }
                dialog.show()
                true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}