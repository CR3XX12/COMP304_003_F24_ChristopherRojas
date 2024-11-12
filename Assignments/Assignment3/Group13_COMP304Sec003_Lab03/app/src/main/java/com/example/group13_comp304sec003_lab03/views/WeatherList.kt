package com.example.group13_comp304sec003_lab03.views

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.group13_comp304sec003_lab03.data.Weather
import com.example.group13_comp304sec003_lab03.data.WeatherResponse
import com.example.group13_comp304sec003_lab03.navigation.ContentType
import com.example.group13_comp304sec003_lab03.navigation.Screens
import com.example.group13_comp304sec003_lab03.viewmodel.WeathersViewModel
import org.koin.androidx.compose.koinViewModel



@SuppressLint("SuspiciousIndentation")
@Composable
fun WeatherList(modifier: Modifier,
                onWeatherClicked: (WeatherResponse) -> Unit) {
    val weathersViewModel: WeathersViewModel = koinViewModel()
    val weathersUIState by weathersViewModel.weathersUIState.collectAsStateWithLifecycle()
        Column(
        modifier = modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = weathersUIState.isLoading
        ) {
            CircularProgressIndicator()
        }
        AnimatedVisibility(
            visible = weathersUIState.weather.isNotEmpty()
        ) {
            LazyColumn {
                items(weathersUIState.weather) { weatherResponse ->
                    WeatherListItem(
                        weather = weatherResponse,
                        onWeatherClicked = onWeatherClicked,
                        onFavoriteClicked = {
                            weathersViewModel.updateWeather(weatherResponse.copy(isFavorite = !weatherResponse.isFavorite))
                        })
                }
            }
        }
        AnimatedVisibility(
            visible = weathersUIState.error != null
        ) {
            Text(text = weathersUIState.error ?: "")
        }
    }
}



