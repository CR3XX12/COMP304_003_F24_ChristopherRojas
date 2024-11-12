package com.example.group13_comp304sec003_lab03.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@TypeConverters(WeathersTypeConverters::class)
@Entity(tableName = "WeatherResponse")
@Serializable
data class WeatherEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    @SerialName("id") val id: Int,


    @Embedded
    @SerialName("coord") val coord: Coord,

    @SerialName("weather") val weather: List<Weather>, // Requires a TypeConverter for List<Weather>

    @ColumnInfo(name = "base")
    @SerialName("base") val base: String,

    @Embedded(prefix = "main_")
    @SerialName("main") val main: Main,

    @ColumnInfo(name = "visibility")
    @SerialName("visibility") val visibility: Int,

    @Embedded(prefix = "wind_")
    @SerialName("wind") val wind: Wind,

    @Embedded(prefix = "clouds_")
    @SerialName("clouds") val clouds: Clouds,

    @ColumnInfo(name = "dt")
    @SerialName("dt") val dt: Long,

    @Embedded(prefix = "sys_")
    @SerialName("sys") val sys: Sys,

    @ColumnInfo(name = "timezone")
    @SerialName("timezone") val timezone: Int,

    @ColumnInfo(name = "name")
    @SerialName("name") val name: String,

    @ColumnInfo(name = "cod")
    @SerialName("cod") val cod: Int,

    @ColumnInfo(defaultValue = "0")
    val isFavorite: Boolean = false

)