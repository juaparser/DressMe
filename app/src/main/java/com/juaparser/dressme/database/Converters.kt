package com.juaparser.dressme.database

import android.net.Uri
import android.renderscript.RenderScript
import androidx.room.TypeConverter
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

}