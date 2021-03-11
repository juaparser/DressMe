package com.juaparser.dressme.database

import androidx.room.*

@Entity(primaryKeys = ["prenda_id", "conjunto_id"])
data class ConjuntoPrendaCrossRef (
        @ColumnInfo(name = "prenda_id") var prenda_id: Long,
        @ColumnInfo(name = "conjunto_id") var conjunto_id: Long
)

data class ConjuntoConPrendas(
    @Embedded var conjunto: Conjunto,
    @Relation(
            entity = Prenda::class,
            entityColumn = "prendaId",
            parentColumn = "conjuntoId",
            associateBy = Junction(
                    value = ConjuntoPrendaCrossRef::class,
                    entityColumn = "prenda_id",
                    parentColumn = "conjunto_id")
    )
    val prendas: List<Prenda>
)

data class PrendaConConjuntos(
        @Embedded var prenda: Prenda,
        @Relation(
                entity = Conjunto::class,
                entityColumn = "conjuntoId",
                parentColumn = "prendaId",
                associateBy = Junction(
                        ConjuntoPrendaCrossRef::class,
                        entityColumn = "conjunto_id",
                        parentColumn = "prenda_id")
    )
    val conjuntos: List<Conjunto>
)