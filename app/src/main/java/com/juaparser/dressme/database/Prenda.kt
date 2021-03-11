package com.juaparser.dressme.database

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Contextual
import java.io.Serializable
import java.util.*

@Entity(tableName = "prenda")
data class Prenda (
        @PrimaryKey(autoGenerate = true) var prendaId: Int = 0,
        @ColumnInfo(name = "name") var name : String,
        @ColumnInfo(name = "image") var image: Uri,
        @ColumnInfo(name = "category") var category: TopCategoria,
        @ColumnInfo(name = "color") var color: Color,
        @ColumnInfo(name = "weather") var weather: Tiempo,
        @ColumnInfo(name = "creationDate") var creationDate: Date,
        @ColumnInfo(name = "purchaseDate") var purchaseDate: Date?,
        @ColumnInfo(name = "uses") var uses: Int = 0,
        @ColumnInfo(name = "brand") var brand: String?,
        @ColumnInfo(name = "size") var size: String?
    )