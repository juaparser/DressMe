package com.juaparser.dressme.database

import androidx.room.*
import com.juaparser.dressme.database.Prenda
import com.juaparser.dressme.database.TopCategoria

@Dao
interface PrendaDao {
    @Query("SELECT * FROM prenda")
    fun getAllPrendas(): MutableList<Prenda>

    @Insert
    fun addPrenda(prenda: Prenda): Long

    @Query("SELECT * FROM prenda WHERE prendaId LIKE :id")
    fun getPrendaById(id: Int): Prenda

    @Query("SELECT * FROM prenda WHERE name LIKE :name")
    fun getPrendaByName(name: String): Prenda

    @Query("SELECT * FROM prenda WHERE category LIKE :category")
    fun getPrendasByCategory(category: TopCategoria): MutableList<Prenda>

    @Query("SELECT * FROM prenda WHERE category LIKE :category AND color IN (:colors) AND weather IN (:weathers)")
    fun getPrendasByFilters(category: TopCategoria, colors: List<Color>, weathers: List<Tiempo>): MutableList<Prenda>

    @Query("SELECT name FROM prenda WHERE category LIKE :category")
    fun getNombrePrendasByCategory(category: TopCategoria): MutableList<String>

    @Update
    fun updatePrenda(prenda: Prenda): Int

    @Delete
    fun deletePrenda(prenda: Prenda): Int

}
