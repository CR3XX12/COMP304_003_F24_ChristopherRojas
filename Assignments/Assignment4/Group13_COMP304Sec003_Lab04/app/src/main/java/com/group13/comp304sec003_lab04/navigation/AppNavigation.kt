package com.group13.comp304sec003_lab04.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.group13.comp304sec003_lab04.CategoryList
import com.group13.comp304sec003_lab04.LandmarkList
import com.group13.comp304sec003_lab04.MapScreen
import com.group13.comp304sec003_lab04.data.LandmarkData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "mainActivity") {

        composable("mainActivity") {
            CategoryList(navController)
        }

        composable("landmarkScreen/historic") {
            LandmarkList(
                navController,
                "Historic",
                LandmarkData.historic,
                onItemClick = { landmarkData ->
                    navController.navigate("mapScreen/${Json.encodeToString(landmarkData)}")
                }
            )
        }
        composable("landmarkScreen/park") {
            LandmarkList(
                navController,
                "Park",
                LandmarkData.park,
                onItemClick = { landmarkData ->
                    navController.navigate("mapScreen/${Json.encodeToString(landmarkData)}")
                }
            )
        }
        composable("landmarkScreen/museum") {
            LandmarkList(
                navController,
                "Museum",
                LandmarkData.museums,
                onItemClick = { landmarkData ->
                    navController.navigate("mapScreen/${Json.encodeToString(landmarkData)}")
                }
            )
        }
        composable("landmarkScreen/touristic") {
            LandmarkList(
                navController,
                "Touristic",
                LandmarkData.touristic,
                onItemClick = { landmarkData ->
                    navController.navigate("mapScreen/${Json.encodeToString(landmarkData)}")
                }
            )
        }

        composable(
            route = "mapScreen/{landmarkData}",
            arguments = listOf(
                navArgument("landmarkData") {
                    type = NavType.StringType
                }
            )) {
            MapScreen(
                navController,
                landmarkData = Json.decodeFromString(it.arguments?.getString("landmarkData") ?: "")
            )
        }
    }
}