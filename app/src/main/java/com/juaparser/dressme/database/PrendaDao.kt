package com.juaparser.dressme.database

import androidx.room.*
import com.juaparser.dressme.database.enums.Color
import com.juaparser.dressme.database.enums.Tiempo
import com.juaparser.dressme.database.enums.TopCategoria

/*
*
* Clase DAO que contiene las operaciones para la entidad Prenda
*
 */

@Dao
interface PrendaDao {
    @Query("SELECT * FROM prenda")
    fun getAllPrendas(): MutableList<Prenda>

    @Insert
    fun addPrenda(prenda: Prenda): Long

    @Query("SELECT * FROM prenda WHERE prendaId LIKE :id")
    fun getPrendaById(id: Int): Prenda

    @Query("SELECT * FROM prenda ORDER BY creationDate DESC")
    fun getPrendasDate(): MutableList<Prenda>

    @Query("SELECT * FROM prenda WHERE name LIKE :name")
    fun getPrendaByName(name: String): Prenda

    @Query("SELECT * FROM prenda WHERE topCategory LIKE :topCategory")
    fun getPrendasByCategory(topCategory: TopCategoria): MutableList<Prenda>

    @Query("SELECT * FROM prenda WHERE topCategory LIKE :topCategory AND favorite LIKE 'true'")
    fun getFavoritesPrendasByCategory(topCategory: TopCategoria): MutableList<Prenda>

    @Query("SELECT * FROM prenda WHERE topCategory LIKE :category AND (color IN (:colors) OR weather IN (:weathers))")
    fun getPrendasByFilters(category: TopCategoria, colors: List<Color>, weathers: List<Tiempo>): MutableList<Prenda>

    @Query("SELECT * FROM prenda WHERE subCategory LIKE :category")
    fun getPrendasBySubcategories(category: String): MutableList<Prenda>

    @Query("SELECT name FROM prenda WHERE topCategory LIKE :category")
    fun getNombrePrendasByCategory(category: TopCategoria): MutableList<String>

    @Update
    fun updatePrenda(prenda: Prenda): Int

    @Delete
    fun deletePrenda(prenda: Prenda): Int

}
