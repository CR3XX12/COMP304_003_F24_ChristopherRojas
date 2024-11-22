package com.example.group13_comp304sec003_lab03.views

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.group13_comp304sec003_lab03.data.WeatherResponse
import com.example.group13_comp304sec003_lab03.viewmodel.WeathersViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeathersScreen(
    modifier: Modifier,
    onWeatherClicked: (WeatherResponse) -> Unit,
) {
    val weathersViewModel: WeathersViewModel = koinViewModel()
    val weathersUIState by weathersViewModel.weathersUIState.collectAsStateWithLifecycle()

    var searchQuery by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                weathersViewModel.searchCity(query)
            },
            label = { Text("Search for a city") },
            placeholder = { Text("Type a city name") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        AnimatedVisibility(visible = weathersUIState.isLoading) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(visible = weathersUIState.weather.isNotEmpty()) {


            Column(
                modifier = modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Display the list of weather items
                LazyColumn {
                    items(weathersUIState.weather) { weatherResponse ->
                        WeatherListItem(
                            weather = weatherResponse,
                            onWeatherClicked = onWeatherClicked,
                            onFavoriteClicked = {
                                weathersViewModel.updateWeather(it)
                                weathersViewModel.searchCity("")
                            }
                        )
                    }
                }
            }
        }

        AnimatedVisibility(visible = weathersUIState.error != null) {
            Text(text = weathersUIState.error ?: "")
        }
    }
}
