package com.example.group13_comp304sec003_lab03.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.group13_comp304sec003_lab03.data.Weather
import com.example.group13_comp304sec003_lab03.views.FavoriteWeathersScreen
import com.example.group13_comp304sec003_lab03.views.WeatherDetailsScreen
import com.example.group13_comp304sec003_lab03.views.WeathersScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    contentType: ContentType,
    navHostController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navHostController,
        startDestination = Screens.WeathersScreen.route
    ) {
        composable(Screens.WeathersScreen.route) {
            WeathersScreen(
                onWeatherClicked = { weather ->
                    navHostController.navigate(
                    "${Screens.WeatherDetailsScreen.route}/${Json.encodeToString(weather)}"
                    )
                },
                modifier = Modifier.fillMaxSize()
            )

        }

        composable( route = "${Screens.WeatherDetailsScreen.route}/{weather}",
            arguments = listOf(
                navArgument("weather"){
                    type = NavType.StringType
                }
            )
            ) {
            WeatherDetailsScreen(
                onBackPressed = {
                    navHostController.popBackStack()
                }, weatherResponse = Json.decodeFromString(it.arguments?.getString("weather") ?: "")
            )

        }

        composable(Screens.FavoriteWeathersScreen.route) {
            FavoriteWeathersScreen( onWeatherClicked = {
                weather -> navHostController.navigate(
                    "${Screens.WeatherDetailsScreen.route}/${Json.encodeToString(weather)}")
            })

        }
    }
}