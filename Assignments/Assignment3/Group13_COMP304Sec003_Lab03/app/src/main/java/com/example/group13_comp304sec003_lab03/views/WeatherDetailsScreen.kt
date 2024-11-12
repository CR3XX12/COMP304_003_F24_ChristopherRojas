package com.example.group13_comp304sec003_lab03.views

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.group13_comp304sec003_lab03.data.WeatherResponse

@Composable
fun WeatherDetailsScreen(weatherResponse: WeatherResponse, onBackPressed: () -> Unit) {
    Row {
        Text(text = weatherResponse.name)
        Text(text = weatherResponse.weather[0].main)
    }
}