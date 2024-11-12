package com.example.group13_comp304sec003_lab03.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.group13_comp304sec003_lab03.data.Weather
import com.example.group13_comp304sec003_lab03.R
import com.example.group13_comp304sec003_lab03.data.WeatherResponse

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeatherListItem(weather: WeatherResponse,
                    onWeatherClicked: (WeatherResponse) -> Unit,
                    onFavoriteClicked: (WeatherResponse) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable { onWeatherClicked(weather) }
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Weather icon based on condition
            val icon = getWeatherIcon(weather.weather[0].main)
            Image(
                painter = painterResource(id = icon),
                contentDescription = "Weather Icon",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Display city name, temperature, and condition
            Column(modifier = Modifier.weight(1f)) {
                Text(text = weather.name)
                Text(text = "${weather.main.temp}°C - ${weather.weather[0].main}")
            }

            // Favorite icon
            Icon(
                modifier = Modifier
                    .clickable {
                        onFavoriteClicked(
                            weather.copy(isFavorite = !weather.isFavorite)
                        )
                    },
                imageVector = if (weather.isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                },
                contentDescription = "Favorite",
                tint = if (weather.isFavorite) {
                    Color.Red
                } else {
                    Color.Gray
                }
            )
        }
    }
}