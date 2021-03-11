package com.juaparser.dressme.ui.subirRopa

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.*
import com.juaparser.dressme.databinding.FragmentSubirRopaBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class SubirRopaFragment : Fragment() {

    private lateinit var binding: FragmentSubirRopaBinding
    private var fileUri: Uri? = null
    private var edit: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSubirRopaBinding.inflate(layoutInflater)

        val categoryAdapter = ArrayAdapter(requireContext(), R.layout.material_sample_item, TopCategoria.values())
        val colorAdapter = ColorAdapter(requireContext(), Color.values())
        val tiempoAdapter = ArrayAdapter(requireContext(), R.layout.material_sample_item, Tiempo.values())

        binding.dropdownTextCategoria.setAdapter(categoryAdapter)
        binding.dropdownTextColores.setAdapter(colorAdapter)
        binding.dropdownTextTiempo.setAdapter(tiempoAdapter)

        edit = arguments?.getBoolean("edit") == true


        if(edit){
            setFields(arguments?.getInt("itemId")!!)
        }

        binding.buttonSubirImagen.setOnClickListener{
            ImagePicker.with(this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start { resultCode, data ->
                        when(resultCode) {
                            Activity.RESULT_OK -> {
                                //Image Uri will not be null for RESULT_OK
                                fileUri = data?.data
                                binding.imageRopa.setImageURI(fileUri)
                            }
                            ImagePicker.RESULT_ERROR -> {
                                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                            }
                            else -> Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
                        }
                    }
        }

        binding.subirPrenda.setOnClickListener {
            subirPrenda()
            findNavController().navigate(R.id.nav_armario)
        }



        return binding.root
    }

    private fun subirPrenda() {
        var category = binding.dropdownTextCategoria.text.toString()
        var color = binding.dropdownTextColores.text.toString()
        var tiempo = binding.dropdownTextTiempo.text.toString()
        var choosenCategory = TopCategoria.Inferior
        var choosenColor = Color.Rojo
        var choosenWeather = Tiempo.Soleado

        for(v in TopCategoria.values()) if(v.name == category) choosenCategory = v
        for(v in Color.values()) if(v.name == color) choosenColor = v

        for(v in Tiempo.values()) if(v.name == tiempo) choosenWeather = v

        doAsync {
            if (edit) {

                val itemId: Int? = arguments?.getInt("itemId")
                val dbPrenda = DressMeApp.database.prendaDao().getPrendaById(itemId!!)

                if(fileUri == null) {
                    fileUri = dbPrenda.image
                }

                val prenda = Prenda(
                        prendaId = dbPrenda.prendaId,
                        name = binding.editNombre.editText?.text.toString(),
                        image = fileUri!!,
                        category = choosenCategory,
                        color = choosenColor,
                        weather = choosenWeather,
                        creationDate = dbPrenda.creationDate,
                        purchaseDate = Date(),
                        uses = dbPrenda.uses,
                        brand = binding.editMarca.editText?.text.toString(),
                        size = binding.editTalla.editText?.text.toString()
                )

                DressMeApp.database.prendaDao().updatePrenda(prenda)

            } else {
                val prenda = Prenda(
                        name = binding.editNombre.editText?.text.toString(),
                        image = fileUri!!,
                        category = choosenCategory,
                        color = choosenColor,
                        weather = choosenWeather,
                        creationDate = Date(),
                        purchaseDate = Date(),
                        uses = 0,
                        brand = binding.editMarca.editText?.text.toString(),
                        size = binding.editTalla.editText?.text.toString()
                )
                DressMeApp.database.prendaDao().addPrenda(prenda)
            }
        }
    }

    private fun setFields(itemId: Int) {
        var prenda: Prenda
        doAsync{
            prenda = DressMeApp.database.prendaDao().getPrendaById(itemId)

            uiThread {
                binding.imageRopa.setImageURI(prenda.image)
                binding.editNombre.editText?.setText(prenda.name)
                binding.dropdownTextCategoria.setText(prenda.category.name, false)
                binding.dropdownTextColores.setText(prenda.color.name, false)
                binding.dropdownTextTiempo.setText(prenda.weather.name, false)
                binding.editMarca.editText?.setText(prenda.brand)
                binding.editTalla.editText?.setText(prenda.size)
            }
        }

        binding.subirPrenda.text = resources.getString(R.string.edit_prenda)
    }


}