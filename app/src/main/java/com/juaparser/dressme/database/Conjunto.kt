package com.juaparser.dressme.database

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.juaparser.dressme.database.enums.Tiempo

/*
*
*  Entidad Conjunto para la base de datos.
* Atributos:
*  - conjuntoId: Identificador para la base de datos
*  - name: Nombre del conjunto
*  - image: Enlace a la galería de la imagen
*  - weather: Tiempo elegido.
*
 */

@Entity(tableName = "conjunto")
data class Conjunto (
    @PrimaryKey(autoGenerate = true) var conjuntoId: Long = 0,
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "image") var image: Uri?,
    @ColumnInfo(name = "weather") var weather: Tiempo?
)