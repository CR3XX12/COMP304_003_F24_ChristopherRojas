package com.example.group13_comp304sec003_lab03.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.example.group13_comp304sec003_lab03.R
import com.example.group13_comp304sec003_lab03.data.WeatherResponse

@Composable
fun WeatherDetailsScreen(weatherResponse: WeatherResponse, onBackPressed: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val icon = getWeatherIcon(weatherResponse.weather[0].main)
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Weather Icon",
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Location: ${weatherResponse.name}")
        Text(text = "Condition: ${weatherResponse.weather[0].main}")
        Text(text = "Temperature: ${(weatherResponse.main.temp - 273.15).toInt()}°C") // Convert to Celsius
    }
}

fun getWeatherIcon(condition: String): Int {
    return when (condition) {
        "Clear" -> R.drawable.sunny_icon
        "Clouds" -> R.drawable.cloudy_icon
        "Rain" -> R.drawable.rain_icon
        else -> R.drawable.default_weather_icon
    }
}
