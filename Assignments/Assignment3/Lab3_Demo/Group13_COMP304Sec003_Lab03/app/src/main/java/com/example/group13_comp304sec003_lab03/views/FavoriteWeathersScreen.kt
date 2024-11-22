package com.example.group13_comp304sec003_lab03.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.group13_comp304sec003_lab03.data.Weather
import com.example.group13_comp304sec003_lab03.data.WeatherResponse
import com.example.group13_comp304sec003_lab03.viewmodel.WeathersViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteWeathersScreen(onWeatherClicked: (WeatherResponse) -> Unit)
{
    val weathersViewModel: WeathersViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        weathersViewModel.getFavoriteWeather()
    }
    val weathers by weathersViewModel.favoriteWeather.collectAsStateWithLifecycle()
    if (weathers.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No favorite weather")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(weathers) { weatherResponse: WeatherResponse ->
                WeatherListItem(
                    weather = weatherResponse,
                    onWeatherClicked = onWeatherClicked,
                    onFavoriteClicked = {
                        weathersViewModel.updateWeather(it)
                    }
                )
            }
        }
    }
}