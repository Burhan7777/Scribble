package com.pzbapps.squiggly.common.domain.utils

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ConverterBoolean {
    @TypeConverter
    fun fromList(value: ArrayList<Boolean>) = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) = Json.decodeFromString<ArrayList<Boolean>>(value)
}