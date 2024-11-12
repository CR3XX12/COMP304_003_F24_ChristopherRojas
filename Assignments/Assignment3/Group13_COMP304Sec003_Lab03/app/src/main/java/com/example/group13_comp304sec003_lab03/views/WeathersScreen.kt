package com.example.group13_comp304sec003_lab03.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.group13_comp304sec003_lab03.data.WeatherResponse
import com.example.group13_comp304sec003_lab03.navigation.ContentType
import com.example.group13_comp304sec003_lab03.viewmodel.WeathersViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WeathersScreen(
    modifier: Modifier,
    onWeatherClicked: (WeatherResponse) -> Unit,
){
    val weathersViewModel: WeathersViewModel = koinViewModel()
    val weathersUIState by weathersViewModel.weathersUIState.collectAsStateWithLifecycle()
    Column(
        modifier =  modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AnimatedVisibility(visible = weathersUIState.isLoading) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(visible = weathersUIState.weather.isNotEmpty()){
            WeatherList(
                modifier = modifier, onWeatherClicked = onWeatherClicked
            )
        }
        
        AnimatedVisibility(visible = weathersUIState.error != null) {
            Text(text = weathersUIState.error ?: "")
        }
    }



}


