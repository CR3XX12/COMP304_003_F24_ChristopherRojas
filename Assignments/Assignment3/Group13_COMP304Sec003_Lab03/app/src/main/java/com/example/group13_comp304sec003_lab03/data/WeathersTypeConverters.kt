package com.example.group13_comp304sec003_lab03.data
import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class WeathersTypeConverters {

    @TypeConverter
    fun fromWeatherList(weather: List<Weather>?): String? {
        return weather?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toWeatherList(data: String?): List<Weather>? {
        return data?.let { Json.decodeFromString(it) }
    }
}