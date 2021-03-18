package com.juaparser.dressme.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Prenda::class, Conjunto::class, ConjuntoPrendaCrossRef::class],
    version = 1,
    exportSchema = false)

@TypeConverters(Converters::class)
abstract class DressMeDatabase : RoomDatabase() {
    abstract fun prendaDao(): PrendaDao
    abstract fun conjuntoDao(): ConjuntoDao
    abstract fun ConjuntoPrendaDao(): ConjuntoPrendaDao
}