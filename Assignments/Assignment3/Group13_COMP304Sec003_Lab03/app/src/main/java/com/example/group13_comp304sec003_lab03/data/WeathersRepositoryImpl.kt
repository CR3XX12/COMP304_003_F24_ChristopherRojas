package com.example.group13_comp304sec003_lab03.data

import android.util.Log
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class WeathersRepositoryImpl(
    private val weathersAPI: WeathersAPI,
    private val dispatcher: CoroutineDispatcher,
    private val weatherDao: WeatherDao
): WeathersRepository {

    override suspend fun getWeather(): Flow<List<WeatherResponse>> {
        return withContext(dispatcher) {
            weatherDao.getWeatherResponse()
                .map { weatherCached ->
                    weatherCached.map { weatherEntity ->
                        WeatherResponse(
                            coord = weatherEntity.coord,
                            weather = weatherEntity.weather,
                            base = weatherEntity.base,
                            main = weatherEntity.main,
                            visibility = weatherEntity.visibility,
                            wind = weatherEntity.wind,
                            clouds = weatherEntity.clouds,
                            dt = weatherEntity.dt,
                            sys = weatherEntity.sys,
                            timezone = weatherEntity.timezone,
                            id = weatherEntity.id,
                            name = weatherEntity.name,
                            cod = weatherEntity.cod,
                            isFavorite = weatherEntity.isFavorite
                        ) }
                }
                .onEach {
                    if (it.isEmpty()) {
                        fetchRemoteWeather("Mexico")
                        fetchRemoteWeather("Seoul")
                        fetchRemoteWeather("Tokyo")
                    }
                }
        }
    }

    override suspend fun fetchRemoteWeather(loc: String) {
        withContext(dispatcher) {
            val response = weathersAPI.fetchWeathers(loc, API_KEY)
            if (response.isSuccessful) {
                val body = response.body()
                weatherDao.insert(WeatherEntity(
                    coord = body!!.coord,
                    weather = body!!.weather,
                    base = body!!.base,
                    main = body!!.main,
                    visibility = body!!.visibility,
                    wind = body!!.wind,
                    clouds = body!!.clouds,
                    dt = body!!.dt,
                    sys = body!!.sys,
                    timezone = body!!.timezone,
                    id = body!!.id,
                    name = body!!.name,
                    cod = body!!.cod,
                    isFavorite = body!!.isFavorite
                )
                )
            }
        }
    }

    override suspend fun updateWeather(weather: WeatherResponse) {
        withContext(dispatcher) {
            weatherDao.update(
                WeatherEntity(
                    coord = weather.coord,
                    weather = weather.weather,
                    base = weather.base,
                    main = weather.main,
                    visibility = weather.visibility,
                    wind = weather.wind,
                    clouds = weather.clouds,
                    dt = weather.dt,
                    sys = weather.sys,
                    timezone = weather.timezone,
                    id = weather.id,
                    name = weather.name,
                    cod = weather.cod,
                    isFavorite = weather.isFavorite
                )
            )
        }
    }

    override suspend fun getFavoriteWeather() : Flow<List<WeatherResponse>> {
        return withContext(dispatcher) {
            weatherDao.getFavoriteWeather()
                .map { weatherCached ->
                    weatherCached.map { weatherEntity ->
                        WeatherResponse(
                            coord = weatherEntity.coord,
                            weather = weatherEntity.weather,
                            base = weatherEntity.base,
                            main = weatherEntity.main,
                            visibility = weatherEntity.visibility,
                            wind = weatherEntity.wind,
                            clouds = weatherEntity.clouds,
                            dt = weatherEntity.dt,
                            sys = weatherEntity.sys,
                            timezone = weatherEntity.timezone,
                            id = weatherEntity.id,
                            name = weatherEntity.name,
                            cod = weatherEntity.cod,
                            isFavorite = weatherEntity.isFavorite
                        )
                    }
                }
        }
    }
}
