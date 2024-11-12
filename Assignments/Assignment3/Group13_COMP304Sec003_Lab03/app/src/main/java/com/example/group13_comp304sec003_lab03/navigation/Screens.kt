package com.example.group13_comp304sec003_lab03.navigation

sealed class Screens(val route: String) {
    object WeathersScreen : Screens("weathers")
    object WeatherDetailsScreen : Screens("weatherDetails")
    object FavoriteWeathersScreen : Screens("favoriteWeathers")
}