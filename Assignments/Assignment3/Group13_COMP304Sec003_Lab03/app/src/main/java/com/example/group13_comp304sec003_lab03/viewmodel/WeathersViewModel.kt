package com.example.group13_comp304sec003_lab03.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.group13_comp304sec003_lab03.data.NetworkResult
import com.example.group13_comp304sec003_lab03.data.WeatherResponse
import com.example.group13_comp304sec003_lab03.data.WeathersRepository
import com.example.group13_comp304sec003_lab03.data.asResult
import com.example.group13_comp304sec003_lab03.views.WeathersUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeathersViewModel(
    private val weathersRepository: WeathersRepository
): ViewModel() {
    val weathersUIState = MutableStateFlow(WeathersUIState())
    private val _favoriteWeather =
        MutableStateFlow<List<WeatherResponse>>(emptyList())
    val favoriteWeather: StateFlow<List<WeatherResponse>> get() = _favoriteWeather

    init {
        getWeather("Mexico")
        getWeather("Seoul")
        getWeather("Tokyo")
    }

    private fun getWeather(loc: String) {
        weathersUIState.value = WeathersUIState(isLoading = true)
        viewModelScope.launch {
            weathersRepository.getWeather().asResult().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        weathersUIState.update {
                            it.copy(isLoading = false, weather = result.data)
                        }
                    }

                    is NetworkResult.Error -> {
                        weathersUIState.update {
                            it.copy(isLoading = false, error = result.error)
                        }
                    }
                }
            }
        }
    }

    fun updateWeather(weatherResponse: WeatherResponse) {
        viewModelScope.launch {
            weathersRepository.updateWeather((weatherResponse))
        }
    }
    fun getFavoriteWeather() {
        viewModelScope.launch {
            weathersRepository.getFavoriteWeather().collect {
                _favoriteWeather.value = it
            }
        }
    }
}