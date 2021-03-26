package com.juaparser.dressme.database

import android.net.Uri
import androidx.room.TypeConverter
import com.juaparser.dressme.database.enums.*
import java.util.*

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun fromBoolean(value: Boolean): String {
        return value.toString()
    }

    @TypeConverter
    fun toBoolean(boolean: String): Boolean {
        return boolean.toBoolean()
    }

    @TypeConverter
    fun fromUri(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun toUri(uri: String): Uri {
        return Uri.parse(uri)
    }

    @TypeConverter
    fun fromTopCategoria(topCategoria: TopCategoria): String {
        return topCategoria.name
    }

    @TypeConverter
    fun toTopCategoria(topCategoria: String): TopCategoria {
        return TopCategoria.valueOf(topCategoria)
    }

    @TypeConverter
    fun fromColor(color: Color): String {
        return color.name
    }

    @TypeConverter
    fun toColor(color: String): Color {
        return Color.valueOf(color)
    }

    @TypeConverter
    fun fromTiempo(tiempo: Tiempo): String {
        return tiempo.name
    }

    @TypeConverter
    fun toTiempo(tiempo: String): Tiempo {
        return Tiempo.valueOf(tiempo)
    }

    @TypeConverter
    fun fromAccesorio(accesorio: Accesorio): String {
        return accesorio.name
    }

    @TypeConverter
    fun toAccesorio(accesorio: String): Accesorio {
        return Accesorio.valueOf(accesorio)
    }

    @TypeConverter
    fun fromSuperior(superior: Superior): String {
        return superior.name
    }

    @TypeConverter
    fun toSuperior(superior: String): Superior {
        return Superior.valueOf(superior)
    }

    @TypeConverter
    fun fromInferior(inferior: Inferior): String {
        return inferior.name
    }

    @TypeConverter
    fun toInferior(inferior: String): Inferior {
        return Inferior.valueOf(inferior)
    }

    @TypeConverter
    fun fromCalzado(calzado: Calzado): String {
        return calzado.name
    }

    @TypeConverter
    fun toCalzado(calzado: String): Calzado {
        return Calzado.valueOf(calzado)
    }

}