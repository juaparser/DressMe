package com.juaparser.dressme.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conjunto")
data class Conjunto (
    @PrimaryKey(autoGenerate = true) var conjuntoId: Long = 0,
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "image") var image: Uri?,
    @ColumnInfo(name = "uses") var uses: Int,
    @ColumnInfo(name = "weather") var weather: Tiempo?
)