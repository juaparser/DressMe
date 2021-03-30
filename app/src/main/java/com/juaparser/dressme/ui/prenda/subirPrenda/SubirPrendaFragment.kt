package com.juaparser.dressme.ui.prenda.subirPrenda

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.juaparser.dressme.BuildConfig
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.*
import com.juaparser.dressme.database.enums.*
import com.juaparser.dressme.databinding.FragmentSubirPrendaBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.DateFormat
import java.util.*

/*
*
* Fragmento para subir una nueva prenda o editarla
*
 */

class SubirPrendaFragment : Fragment() {

    private lateinit var binding: FragmentSubirPrendaBinding
    private var fileUri: Uri? = null
    private var edit: Boolean = false
    private var fechaCompra: Date? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = FragmentSubirPrendaBinding.inflate(layoutInflater)

        binding.fechaCompra.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Seleccione fecha")
                    .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                    .build()

            datePicker.addOnPositiveButtonClickListener {
                fechaCompra = Date(datePicker.selection!!)
                val dateFormat = DateFormat.getDateInstance()
                binding.fechaCompra.text = dateFormat.format(fechaCompra!!)
            }

            datePicker.show(requireFragmentManager(), "tag")
        }

        var tiempos = Tiempo.values().toMutableList()
        tiempos.remove(Tiempo.Indefinido)

        val categoryAdapter = ArrayAdapter(requireContext(), R.layout.material_sample_item, TopCategoria.values())
        val colorAdapter = ColorAdapter(requireContext(), Color.values())
        val tiempoAdapter = ArrayAdapter(requireContext(), R.layout.material_sample_item, tiempos)

        binding.dropdownTextCategoria.setAdapter(categoryAdapter)
        binding.dropdownTextColores.setAdapter(colorAdapter)
        binding.dropdownTextTiempo.setAdapter(tiempoAdapter)


        binding.dropdownTextCategoria.setOnItemClickListener { parent, view, position, id ->
            binding.dropdownTextSubCategoria.isEnabled = true
            binding.dropdownTextSubCategoria.setText("")
            var subCategories = when(binding.dropdownTextCategoria.text.toString()) {
                "Accesorio" -> Accesorio.values()
                "Superior" -> Superior.values()
                "Inferior" -> Inferior.values()
                "Calzado" -> Calzado.values()
                else -> Accesorio.values()
            }
            val subCategoryAdapter = ArrayAdapter(requireContext(), R.layout.material_sample_item, subCategories)
            binding.dropdownTextSubCategoria.setAdapter(subCategoryAdapter)
        }

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
                        }
                    }
        }

        binding.subirPrenda.setOnClickListener {
            if(subirPrenda()) findNavController().navigate(R.id.action_nav_subirRopa_to_nav_armario)
        }

        return binding.root
    }

    private fun subirPrenda(): Boolean {
        var category = binding.dropdownTextCategoria.text.toString()
        var color = binding.dropdownTextColores.text.toString()
        var tiempo = binding.dropdownTextTiempo.text.toString()
        var choosenCategory = TopCategoria.Inferior
        var choosenColor = Color.Rojo
        var choosenWeather = Tiempo.Indefinido
        var res = true

        for(v in TopCategoria.values()) if(v.name == category) choosenCategory = v
        for(v in Color.values()) if(v.name == color) choosenColor = v
        for(v in Tiempo.values()) if(v.name == tiempo) choosenWeather = v


        if(checkForm()) {
            doAsync {
                if (edit) {

                    val itemId: Int? = arguments?.getInt("itemId")
                    val dbPrenda = DressMeApp.database.prendaDao().getPrendaById(itemId!!)

                    if (fileUri == null) {
                        fileUri = dbPrenda.image
                    }


                    val prenda = Prenda(
                            prendaId = dbPrenda.prendaId,
                            name = binding.editNombre.editText?.text.toString(),
                            image = fileUri!!,
                            topCategory = choosenCategory,
                            subCategory = binding.dropdownTextSubCategoria.text.toString(),
                            color = choosenColor,
                            weather = choosenWeather,
                            creationDate = dbPrenda.creationDate,
                            purchaseDate = fechaCompra,
                            brand = binding.editMarca.editText?.text.toString(),
                            size = binding.editTalla.editText?.text.toString(),
                            favorite = dbPrenda.favorite
                    )

                    DressMeApp.database.prendaDao().updatePrenda(prenda)
                    Snackbar.make(binding.root, "Prenda ${prenda.name} actualizada correctamente.", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(resources.getColor(R.color.confirm))
                            .show()

                } else {
                    if (fileUri == null) {
                        fileUri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + R.drawable.imagen)
                    }

                    val prenda = Prenda(
                            name = binding.editNombre.editText?.text.toString(),
                            image = fileUri!!,
                            topCategory = choosenCategory,
                            subCategory = binding.dropdownTextSubCategoria.text.toString(),
                            color = choosenColor,
                            weather = choosenWeather,
                            creationDate = Date(),
                            purchaseDate = fechaCompra,
                            brand = binding.editMarca.editText?.text.toString(),
                            size = binding.editTalla.editText?.text.toString(),
                            favorite = false
                    )

                    DressMeApp.database.prendaDao().addPrenda(prenda)

                    Snackbar.make(binding.root, "Prenda ${prenda.name} subida correctamente.", Snackbar.LENGTH_LONG)
                            .setBackgroundTint(resources.getColor(R.color.confirm))
                            .show()
                }
            }
        } else {
            res = false
        }

        return res
    }


    private fun setFields(itemId: Int) {
        var prenda: Prenda
        val dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY)

        doAsync{
            prenda = DressMeApp.database.prendaDao().getPrendaById(itemId)

            uiThread {
                binding.imageRopa.setImageURI(prenda.image)
                binding.editNombre.editText?.setText(prenda.name)
                binding.dropdownTextCategoria.setText(prenda.topCategory.name, false)
                binding.dropdownTextSubCategoria.setText(prenda.subCategory, false)
                binding.dropdownTextColores.setText(prenda.color.name, false)
                binding.dropdownTextTiempo.setText(prenda.weather?.name, false)
                binding.editMarca.editText?.setText(prenda.brand)
                binding.editTalla.editText?.setText(prenda.size)
                if(prenda.purchaseDate != null) binding.fechaCompra.text = dateFormat.format(prenda.purchaseDate!!)
            }
        }

        binding.subirPrenda.text = resources.getString(R.string.edit_prenda)
    }

    private fun checkForm(): Boolean {
        var res = true
        val name = binding.editNombre.editText?.text.toString()
        val category = binding.dropdownCategoria.editText?.text.toString()
        val color = binding.dropdownColores.editText?.text.toString()

        if(name.isBlank() || name.isEmpty()){
            binding.editNombre.error = getString(R.string.error_nombre)
            res = false
        }else {
            binding.editNombre.error = null
        }
        if(category.isBlank() || category.isEmpty()){
            binding.dropdownCategoria.error = getString(R.string.error_category)
            res = false
        } else binding.dropdownCategoria.error = null

        if(color.isBlank() || color.isEmpty()){
            binding.dropdownColores.error = getString(R.string.error_color)
            res = false
        } else  binding.dropdownColores.error = null

        return res
    }


}