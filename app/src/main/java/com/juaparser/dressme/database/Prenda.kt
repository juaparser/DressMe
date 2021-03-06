package com.juaparser.dressme.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juaparser.dressme.database.enums.Color
import com.juaparser.dressme.database.enums.Tiempo
import com.juaparser.dressme.database.enums.TopCategoria
import java.util.*

/*
*
*  Entidad Prenda para la base de datos.
*
* Atributos:
*  - prendaId: Identificador para la base de datos
*  - name: Nombre de la prenda
*  - image: Enlace a la galería de la imagen
*  - topCategory: Categoría superior de la prenda.
*  - subCategory: Subcategoría.
*  - color: Color elegido para la prenda.
*  - weather: Tiempo elegido.
*  - creationDate: Fecha de creación.
*  - purchaseDate: Fecha de compra.
*  - brand: Marca.
*  - size: Talla.
*  - favorite: Si la prenda es favorita.
*
 */


@Entity(tableName = "prenda")
data class Prenda (
        @PrimaryKey(autoGenerate = true) var prendaId: Int = 0,
        @ColumnInfo(name = "name") var name : String,
        @ColumnInfo(name = "image") var image: Uri?,
        @ColumnInfo(name = "topCategory") var topCategory: TopCategoria,
        @ColumnInfo(name = "subCategory") var subCategory: String?,
        @ColumnInfo(name = "color") var color: Color,
        @ColumnInfo(name = "weather") var weather: Tiempo?,
        @ColumnInfo(name = "creationDate") var creationDate: Date,
        @ColumnInfo(name = "purchaseDate") var purchaseDate: Date?,
        @ColumnInfo(name = "brand") var brand: String?,
        @ColumnInfo(name = "size") var size: String?,
        @ColumnInfo(name="favorite") var favorite: Boolean
    )