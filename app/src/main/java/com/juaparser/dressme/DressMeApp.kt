package com.juaparser.dressme

import android.app.Application
import android.widget.CheckBox
import androidx.room.Room
import com.juaparser.dressme.database.DressMeDatabase
import com.juaparser.dressme.database.Prenda

class DressMeApp: Application() {

    companion object {

        lateinit var database: DressMeDatabase
        lateinit var listCheckboxColores: MutableList<String>
        lateinit var listCheckboxPrendas: MutableList<String>
        lateinit var listCheckboxTiempo: MutableList<String>

        lateinit var listPrendas: MutableList<Prenda>
    }

    override fun onCreate() {
        super.onCreate()
        DressMeApp.database = Room.databaseBuilder(
                this, DressMeDatabase::class.java, "dressme-db").build()

        listCheckboxColores = mutableListOf()
        listCheckboxPrendas = mutableListOf()
        listCheckboxTiempo = mutableListOf()
        listPrendas = mutableListOf()
    }
}