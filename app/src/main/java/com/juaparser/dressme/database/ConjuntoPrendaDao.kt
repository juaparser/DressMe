package com.juaparser.dressme.database

import androidx.room.*


@Dao
interface ConjuntoPrendaDao {

    @Transaction
    @Query("SELECT * FROM conjunto")
    fun getConjuntosConPrendas(): MutableList<ConjuntoConPrendas>

    @Transaction
    @Query("SELECT * FROM conjunto WHERE conjuntoId LIKE :conjuntoId")
    fun getConjuntoConPrendas(conjuntoId: Long): ConjuntoConPrendas

    @Transaction
    @Query("SELECT * FROM prenda")
    fun getPrendasConConjuntos(): MutableList<PrendaConConjuntos>

    @Transaction
    @Query("SELECT * FROM conjuntoPrendaCrossRef WHERE conjunto_id LIKE :id")
    fun getAllCrossRef(id: Long): MutableList<ConjuntoPrendaCrossRef>

    @Transaction
    @Query("SELECT * FROM conjuntoPrendaCrossRef")
    fun getAllCrossRef(): MutableList<ConjuntoPrendaCrossRef>

    @Transaction
    @Query("DELETE FROM conjuntoPrendaCrossRef WHERE prenda_id LIKE :prendaId")
    fun deleteCrossRefPrenda(prendaId: Long): Int

    @Transaction
    @Query("DELETE FROM conjuntoPrendaCrossRef WHERE conjunto_id LIKE :conjuntoId AND prenda_id LIKE :prendaId")
    fun deleteCrossRef(conjuntoId: Long, prendaId: Long): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPrendaConConjunto(conjuntoPrendaCrossRef: ConjuntoPrendaCrossRef): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updatePrendaConConjunto(conjuntoPrendaCrossRef: ConjuntoPrendaCrossRef): Int

    @Delete
    fun deletePrendaConConjunto(conjuntoPrendaCrossRef: ConjuntoPrendaCrossRef): Int

    @Delete
    fun deletePrendasConConjunto(conjuntoPrendaCrossRef: List<ConjuntoPrendaCrossRef>): Int

}
