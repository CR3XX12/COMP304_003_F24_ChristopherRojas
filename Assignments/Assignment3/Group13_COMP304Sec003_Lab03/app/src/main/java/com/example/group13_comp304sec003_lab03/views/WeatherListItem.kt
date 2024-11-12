package com.example.group13_comp304sec003_lab03.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.group13_comp304sec003_lab03.data.Weather
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
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable {
                    onWeatherClicked(weather)
                }

        ) {
            //TODO: Update Item List UI
            Text(text = weather.name)
            Text(text = weather.weather[0].main)

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
                },
            )
        }
    }
}