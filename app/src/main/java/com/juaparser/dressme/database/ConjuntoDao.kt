package com.juaparser.dressme.database

import androidx.room.*
import com.juaparser.dressme.database.Conjunto

/*
*
* Clase DAO que contiene las operaciones para la entidad Conjunto
*
 */

@Dao
interface ConjuntoDao {
    @Query("SELECT * FROM conjunto")
    fun getAllConjuntos(): MutableList<Conjunto>

    @Insert
    fun addConjunto(conjunto: Conjunto): Long

    @Query("SELECT * FROM conjunto WHERE conjuntoId LIKE :id")
    fun getConjuntoById(id: Long): Conjunto

    @Update
    fun updateConjunto(conjunto: Conjunto): Int

    @Delete
    fun deleteConjunto(conjunto: Conjunto): Int

}
