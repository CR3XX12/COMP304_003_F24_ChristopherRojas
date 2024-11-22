package com.example.group13_comp304sec003_lab03.views

import com.example.group13_comp304sec003_lab03.data.WeatherResponse

data class WeathersUIState(
    val isLoading: Boolean = false,
    val weather: List<WeatherResponse> = emptyList(),
    val error: String? = null
)