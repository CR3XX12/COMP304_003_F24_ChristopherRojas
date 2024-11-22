package com.example.group13_comp304sec003_lab03.data

import kotlinx.coroutines.flow.Flow

interface WeathersRepository {
    suspend fun getWeather(): Flow<List<WeatherResponse>>
    suspend fun fetchRemoteWeather(loc: String)
    suspend fun updateWeather(weather: WeatherResponse)
    suspend fun getFavoriteWeather(): Flow<List<WeatherResponse>>
}