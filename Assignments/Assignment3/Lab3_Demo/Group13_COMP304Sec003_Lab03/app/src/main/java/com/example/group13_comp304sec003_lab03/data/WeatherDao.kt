package com.example.group13_comp304sec003_lab03.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM WeatherResponse")
    fun getWeatherResponse(): Flow<List<WeatherEntity>>

    @Query("SELECT * FROM WeatherResponse WHERE id = :id")
    suspend fun getWeather(id: Int): WeatherEntity

    @Update
    suspend fun update(weatherEntity: WeatherEntity)
    @Query("SELECT * FROM WeatherResponse WHERE isFavorite = 1")
    fun getFavoriteWeather(): Flow<List<WeatherEntity>>
}