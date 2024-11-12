package com.example.group13_comp304sec003_lab03.data

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "5cf2c6f4f012629614f14441bcdea4b2"

interface WeathersAPI {
    @GET("weather")
    suspend fun fetchWeathers(
        @Query("q") tag: String,
        @Query("appid") key: String,
    ): Response<WeatherResponse>
}