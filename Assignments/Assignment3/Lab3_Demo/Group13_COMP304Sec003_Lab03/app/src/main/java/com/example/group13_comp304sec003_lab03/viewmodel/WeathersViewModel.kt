package com.example.group13_comp304sec003_lab03.viewmodel

import android.util.Log
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
) : ViewModel() {
    val weathersUIState = MutableStateFlow(WeathersUIState())
    private val _favoriteWeather = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val favoriteWeather: StateFlow<List<WeatherResponse>> get() = _favoriteWeather

    // Local list to manage the weather data for better control over order
    private val displayedWeatherList = mutableListOf<WeatherResponse>()

    init {
        loadDefaultCities()
    }

    // Loads default cities on initialization
    fun loadDefaultCities() {
        listOf("Mexico", "Seoul", "Tokyo").forEach { getWeather(it) }
    }

    private fun getWeather(loc: String) {
        weathersUIState.value = WeathersUIState(isLoading = true)
        viewModelScope.launch {
            weathersRepository.fetchRemoteWeather(loc)  // Fetch each city's weather data

            // Collect the updated weather list
            weathersRepository.getWeather().asResult().collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        // Add each fetched city to our managed list, avoiding duplicates
                        val newWeatherData = result.data.find { it.name.equals(loc, ignoreCase = true) }
                        if (newWeatherData != null) {
                            displayedWeatherList.removeAll { it.name.equals(newWeatherData.name, ignoreCase = true) }
                            displayedWeatherList.add(newWeatherData)
                        }

                        // Update UI state with the updated list
                        weathersUIState.update {
                            it.copy(isLoading = false, weather = displayedWeatherList)
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


    fun searchCity(query: String) {
        viewModelScope.launch {
            if (query.isNotBlank()) {
                weathersUIState.value = WeathersUIState(isLoading = true)

                // Fetch weather data for the searched city
                weathersRepository.fetchRemoteWeather(query)

                // Retrieve the updated list and move the searched city to the top
                weathersRepository.getWeather().asResult().collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            // Find the searched city in the result
                            val searchedCity = result.data.find { it.name.equals(query, ignoreCase = true) }

                            if (searchedCity != null) {
                                // Remove it if it exists already and add to the top
                                displayedWeatherList.removeAll { it.name.equals(query, ignoreCase = true) }
                                displayedWeatherList.add(0, searchedCity)
                            }

                            // Update UI state with the modified list
                            weathersUIState.update {
                                it.copy(isLoading = false, weather = displayedWeatherList)
                            }
                        }
                        is NetworkResult.Error -> {
                            weathersUIState.update {
                                it.copy(isLoading = false, error = result.error)
                            }
                        }
                    }
                }
            } else {
                loadDefaultCities()
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
