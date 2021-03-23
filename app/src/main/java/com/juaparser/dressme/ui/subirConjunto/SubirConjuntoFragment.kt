package com.juaparser.dressme.ui.subirConjunto

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
import androidx.navigation.fragment.findNavController
import com.github.dhaval2404.imagepicker.ImagePicker
import com.juaparser.dressme.BuildConfig
import com.juaparser.dressme.DressMeApp
import com.juaparser.dressme.R
import com.juaparser.dressme.database.*
import com.juaparser.dressme.databinding.FragmentSubirConjuntoBinding
import com.juaparser.dressme.databinding.FragmentSubirRopaBinding
import com.juaparser.dressme.ui.subirRopa.ColorAdapter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class SubirConjuntoFragment : Fragment() {

    private lateinit var binding: FragmentSubirConjuntoBinding
    private var edit: Boolean = false
    private var fileUri: Uri? = null
    private var dbAccesorio: Prenda? = null
    private var dbSuperior: Prenda? = null
    private var dbInferior: Prenda? = null
    private var dbZapatos: Prenda? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubirConjuntoBinding.inflate(layoutInflater)
        val view = binding.root

        val tiempoAdapter = ArrayAdapter(requireContext(), R.layout.material_sample_item, Tiempo.values())

        binding.dropdownTextTiempo.setAdapter(tiempoAdapter)

        doAsync{
            var accesorios = DressMeApp.database.prendaDao().getNombrePrendasByCategory(TopCategoria.Accesorio)
            val superiores = DressMeApp.database.prendaDao().getNombrePrendasByCategory(TopCategoria.Superior)
            val inferiores = DressMeApp.database.prendaDao().getNombrePrendasByCategory(TopCategoria.Inferior)
            val zapatos = DressMeApp.database.prendaDao().getNombrePrendasByCategory(TopCategoria.Calzado)

            uiThread {
                binding.searchAccesorio.setAdapter(ArrayAdapter<String>(requireContext(), R.layout.material_sample_item, accesorios))
                binding.searchSuperior.setAdapter(ArrayAdapter<String>(requireContext(), R.layout.material_sample_item, superiores))
                binding.searchInferior.setAdapter(ArrayAdapter<String>(requireContext(), R.layout.material_sample_item, inferiores))
                binding.searchZapatos.setAdapter(ArrayAdapter<String>(requireContext(), R.layout.material_sample_item, zapatos))
            }
        }

        edit = arguments?.getBoolean("edit") == true


        if(edit){
            setFields(arguments?.getLong("itemId")!!)
        }
        if(DressMeApp.listPrendas.isNotEmpty()){
            setFieldsGenerate()
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
                            binding.imageConjunto.setImageURI(fileUri)
                        }
                        ImagePicker.RESULT_ERROR -> {
                            Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                        }
                        else -> Toast.makeText(context, "Task Cancelled", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.subirConjunto.setOnClickListener {
            DressMeApp.listPrendas = mutableListOf()
            DressMeApp.listCheckboxTiempo = mutableListOf()
            DressMeApp.listCheckboxColores = mutableListOf()
            DressMeApp.listCheckboxPrendas = mutableListOf()
            subirConjunto()
            findNavController().navigate(R.id.nav_misConjuntos)
        }

        return view
    }

    private fun subirConjunto() {
        var tiempo = binding.dropdownTextTiempo.text.toString()
        var choosenWeather = Tiempo.Soleado

        for(v in Tiempo.values()) if(v.name == tiempo) choosenWeather = v

        doAsync {
            if (edit) {
                Log.e("PAELLA", "ENTRAMOS EN EDIT")
                val itemId: Long? = arguments?.getLong("itemId")
                val dbConjunto = DressMeApp.database.conjuntoDao().getConjuntoById(itemId!!)

                if(fileUri == null) {
                    fileUri = dbConjunto.image
                }

                Log.e("PAELLA", "DB CONJUNTO ES $dbConjunto")
                val conjunto = Conjunto(
                    conjuntoId = dbConjunto.conjuntoId,
                    name = binding.editNombre.editText?.text.toString(),
                    image = fileUri!!,
                    weather = choosenWeather,
                    uses = dbConjunto.uses
                )
                val okok = DressMeApp.database.ConjuntoPrendaDao().getAllCrossRef(dbConjunto.conjuntoId)
                Log.e("PAELLA", "CROSS REF DB CONJUNTO $okok")

                DressMeApp.database.conjuntoDao().updateConjunto(conjunto)

                Log.e("PAELLA", "CONJUNTO UPDATEADO $conjunto")

                var accesorio: String? = binding.searchAccesorio.text.toString()
                var superior: String? = binding.searchSuperior.text.toString()
                var inferior: String? = binding.searchInferior.text.toString()
                var zapatos: String? = binding.searchZapatos.text.toString()

                val dao = DressMeApp.database.prendaDao()

                var prendaAccesorio: Prenda?
                var prendaSuperior: Prenda?
                var prendaInferior: Prenda?
                var prendaZapatos: Prenda?

                if(!accesorio.isNullOrBlank()) {
                    prendaAccesorio = dao.getPrendaByName(accesorio)
                    if(dbAccesorio!=null){
                        // Significa que ya tenia superior
                        if(dbAccesorio!!.prendaId != prendaAccesorio.prendaId){
                            DressMeApp.database.ConjuntoPrendaDao().deleteCrossRef(dbConjunto.conjuntoId, dbAccesorio!!.prendaId.toLong())
                            val cross = ConjuntoPrendaCrossRef(prendaAccesorio.prendaId.toLong(),conjunto.conjuntoId)
                            DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                        } // else significa que es el mismo y no se ha cambiado
                    } else {
                        val cross = ConjuntoPrendaCrossRef(prendaAccesorio.prendaId.toLong(),conjunto.conjuntoId)
                        DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                    }
                } else {
                    if(dbAccesorio!=null) {
                        DressMeApp.database.ConjuntoPrendaDao().deleteCrossRef(dbConjunto.conjuntoId, dbAccesorio!!.prendaId.toLong())
                    }
                }
                if(!superior.isNullOrBlank()) {
                    prendaSuperior = dao.getPrendaByName(superior)
                    if(dbSuperior!=null){
                        // Significa que ya tenia superior
                        if(dbSuperior!!.prendaId != prendaSuperior.prendaId){
                            DressMeApp.database.ConjuntoPrendaDao().deleteCrossRef(dbConjunto.conjuntoId, dbSuperior!!.prendaId.toLong())
                            val cross = ConjuntoPrendaCrossRef(prendaSuperior.prendaId.toLong(),conjunto.conjuntoId)
                            DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                        } // else significa que es el mismo y no se ha cambiado
                    } else {
                        val cross = ConjuntoPrendaCrossRef(prendaSuperior.prendaId.toLong(),conjunto.conjuntoId)
                        DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                    }
                } else {
                    if(dbSuperior!=null) {
                        DressMeApp.database.ConjuntoPrendaDao().deleteCrossRef(dbConjunto.conjuntoId, dbSuperior!!.prendaId.toLong())
                    }
                }

                if(!inferior.isNullOrBlank()){
                    prendaInferior = dao.getPrendaByName(inferior)
                    if(dbInferior!=null){
                        // Significa que ya tenia superior
                        if(dbInferior!!.prendaId != prendaInferior.prendaId){
                            DressMeApp.database.ConjuntoPrendaDao().deleteCrossRef(dbConjunto.conjuntoId, dbInferior!!.prendaId.toLong())
                            val cross = ConjuntoPrendaCrossRef(prendaInferior.prendaId.toLong(),conjunto.conjuntoId)
                            DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                        } // else significa que es el mismo y no se ha cambiado
                    } else {
                        val cross = ConjuntoPrendaCrossRef(prendaInferior.prendaId.toLong(),conjunto.conjuntoId)
                        DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                    }
                } else {
                    if(dbInferior!=null) {
                        DressMeApp.database.ConjuntoPrendaDao().deleteCrossRef(dbConjunto.conjuntoId, dbInferior!!.prendaId.toLong())
                    }
                }
                if(!zapatos.isNullOrBlank()) {
                    prendaZapatos = dao.getPrendaByName(zapatos)
                    if(dbZapatos!=null){
                        // Significa que ya tenia superior
                        if(dbZapatos!!.prendaId != prendaZapatos.prendaId){
                            DressMeApp.database.ConjuntoPrendaDao().deleteCrossRef(dbConjunto.conjuntoId, dbZapatos!!.prendaId.toLong())
                            val cross = ConjuntoPrendaCrossRef(prendaZapatos.prendaId.toLong(),conjunto.conjuntoId)
                            DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                        } // else significa que es el mismo y no se ha cambiado
                    } else {
                        val cross = ConjuntoPrendaCrossRef(prendaZapatos.prendaId.toLong(),conjunto.conjuntoId)
                        DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                    }
                } else {
                    if(dbZapatos!=null) {
                        DressMeApp.database.ConjuntoPrendaDao().deleteCrossRef(dbConjunto.conjuntoId, dbZapatos!!.prendaId.toLong())
                    }
                }

            } else {

                if (fileUri == null) {
                    fileUri = Uri.parse("android.resource://" + BuildConfig.APPLICATION_ID + "/" + R.drawable.imagen)
                }

                val conjunto = Conjunto(
                    name = binding.editNombre.editText?.text.toString(),
                    image = fileUri!!,
                    weather = choosenWeather,
                    uses = 0
                )

                var conjuntoId = DressMeApp.database.conjuntoDao().addConjunto(conjunto)

                var accesorio: String? = binding.searchAccesorio.text.toString()
                var superior: String? = binding.searchSuperior.text.toString()
                var inferior: String? = binding.searchInferior.text.toString()
                var zapatos: String? = binding.searchZapatos.text.toString()

                 val dao = DressMeApp.database.prendaDao()

                var prendaAccesorio: Prenda?
                var prendaSuperior: Prenda?
                var prendaInferior: Prenda?
                var prendaZapatos: Prenda?

                if(!accesorio.isNullOrBlank()) {
                    prendaAccesorio = dao.getPrendaByName(accesorio)
                    val cross = ConjuntoPrendaCrossRef(prendaAccesorio.prendaId.toLong(), conjuntoId.toLong())
                    DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                }
                if(!superior.isNullOrBlank()) {
                    prendaSuperior = dao.getPrendaByName(superior)
                    val cross = ConjuntoPrendaCrossRef(prendaSuperior.prendaId.toLong(), conjuntoId.toLong())
                    DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                }
                if(!inferior.isNullOrBlank()){
                    prendaInferior = dao.getPrendaByName(inferior)
                    val cross = ConjuntoPrendaCrossRef(prendaInferior.prendaId.toLong(), conjuntoId.toLong())
                    DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                }
                if(!zapatos.isNullOrBlank()) {
                    prendaZapatos = dao.getPrendaByName(zapatos)
                    val cross = ConjuntoPrendaCrossRef(prendaZapatos.prendaId.toLong(), conjuntoId.toLong())
                    DressMeApp.database.ConjuntoPrendaDao().addPrendaConConjunto(cross)
                }
            }
        }
    }

    private fun setFields(itemId: Long) {
        var conjunto: Conjunto
        doAsync{
            conjunto = DressMeApp.database.conjuntoDao().getConjuntoById(itemId)

            var prendas = DressMeApp.database.ConjuntoPrendaDao().getConjuntoConPrendas(conjunto.conjuntoId).prendas

            for(p in prendas) {
                when(p.topCategory){
                    TopCategoria.Accesorio -> dbAccesorio = p
                    TopCategoria.Superior -> dbSuperior = p
                    TopCategoria.Inferior -> dbInferior = p
                    TopCategoria.Calzado -> dbZapatos = p
                }
            }
            uiThread {
                binding.imageConjunto.setImageURI(conjunto.image)
                binding.editNombre.editText?.setText(conjunto.name)
                if (dbAccesorio != null) binding.searchAccesorio.setText(dbAccesorio!!.name)
                if (dbSuperior != null) binding.searchSuperior.setText(dbSuperior!!.name)
                if (dbInferior != null) binding.searchInferior.setText(dbInferior!!.name)
                if (dbZapatos != null) binding.searchZapatos.setText(dbZapatos!!.name)

                binding.dropdownTextTiempo.setText(conjunto.weather?.name, false)
            }
        }

        binding.subirConjunto.text = resources.getString(R.string.edit_prenda)
    }


    private fun setFieldsGenerate() {
        doAsync{

            var prendas = DressMeApp.listPrendas
            var accesorio: Prenda? = null
            var superior: Prenda? = null
            var inferior: Prenda? = null
            var zapatos: Prenda? = null

            for(p in prendas) {
                when(p.topCategory){
                    TopCategoria.Accesorio -> accesorio = p
                    TopCategoria.Superior -> superior = p
                    TopCategoria.Inferior -> inferior = p
                    TopCategoria.Calzado -> zapatos = p
                }
            }


            uiThread {
                if (accesorio != null) binding.searchAccesorio.setText(accesorio.name)
                if (superior != null) binding.searchSuperior.setText(superior.name)
                if (inferior != null) binding.searchInferior.setText(inferior.name)
                if (zapatos != null) binding.searchZapatos.setText(zapatos.name)
            }
        }

    }

    private fun checkForm() : Boolean {
        var res = true
        val name = binding.editNombre.editText?.text.toString()
        val accesorio = binding.searchAccesorio.text.toString()
        val superior = binding.searchSuperior.text.toString()
        val inferior = binding.searchInferior.text.toString()
        val calzado = binding.searchZapatos.text.toString()

        if(name.isBlank() || name.isEmpty()){
            binding.editNombre.error = getString(R.string.error_nombre)
            res = false
        } else binding.editNombre.error = null

        if((accesorio.isEmpty() || accesorio.isBlank()) && (superior.isEmpty() || superior.isBlank()) &&
                (inferior.isEmpty() || inferior.isBlank()) && (calzado.isEmpty() || calzado.isBlank())) {
            binding.searchAccesorio.error = getString(R.string.error_prendas)
        } else {
            binding.searchAccesorio.error = null
        }

        return res
    }
}