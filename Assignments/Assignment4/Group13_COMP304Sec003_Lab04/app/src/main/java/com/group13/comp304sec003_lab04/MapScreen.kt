package com.group13.comp304sec003_lab04

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.group13.comp304sec003_lab04.data.Landmark
import com.group13.comp304sec003_lab04.data.LandmarkData
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(navController: NavHostController, landmarkData: Landmark, ) {
    val coroutineScope = rememberCoroutineScope()
    var showMapCard by remember { mutableStateOf(false) }
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(LocalContext.current)

    DisposableEffect(Unit) {
        // Lifecycle cleanup
        onDispose {
            Log.d("LandmarkList", "Cleaning Map resources")
        }
    }

    // Map UI state
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(landmarkData.lat, landmarkData.lng),
            15f
        )
    }

    // Permission handling
    val locationPermissionState = rememberPermissionState(ACCESS_FINE_LOCATION)

    LaunchedEffect(Unit) {
        if (!locationPermissionState.status.isGranted) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        // Google Map Composable
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = MapUiSettings(zoomControlsEnabled = false), // Disable default controls
            properties = MapProperties(isMyLocationEnabled = false),
            onMapLongClick = { latLng ->
                showMapCard = true

                val offsetLatLng = LatLng(landmarkData.lat + 0.002, landmarkData.lng)
                coroutineScope.launch {
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(offsetLatLng, 16f),
                        250
                    )
                }
            }
        ) {
            Marker(
                state = rememberMarkerState(position = LatLng(landmarkData.lat, landmarkData.lng)),
                title = landmarkData.title,
                snippet = landmarkData.subtitle
            )
        }

        // Bottom-right corner buttons
        Column(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // Add spacing between FABs
        ) {
            // Locate Me FAB
            FloatingActionButton(
                onClick = {
                    // Request the user's location
                    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                        location?.let {
                            // Move map to user's current location
                            coroutineScope.launch {
                                cameraPositionState.animate(
                                    CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)),
                                    250
                                )
                            }
                        }
                    }
                },
                containerColor = colorResource(id = R.color.mexgreen),
                contentColor = Color.White,
                modifier = Modifier.size(38.dp) // Standard FAB size
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_locate),
                    contentDescription = "Locate Me",
                    modifier = Modifier.size(20.dp)
                )
            }

            // Zoom In FAB
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.zoomIn(),
                            durationMs = 250
                        )
                    }
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.size(38.dp) // Standard FAB size
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Zoom In",
                    modifier = Modifier.size(18.dp)
                )
            }

            // Zoom Out FAB
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(
                            update = CameraUpdateFactory.zoomOut(),
                            durationMs = 250
                        )
                    }
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_minus), // Replace with your minus icon
                    contentDescription = "Zoom Out",
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }

    if (showMapCard) {
        Box(modifier = Modifier.padding(horizontal = 32.dp, vertical = 48.dp)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        BorderStroke(4.dp, Color.White), // White border with 2.dp thickness
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Image Section
                    Image(
                        painter = painterResource(id = landmarkData.image),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(180.dp)
                            .fillMaxWidth()
                    )
                    // Text Section
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(vertical = 10.dp, horizontal = 12.dp)
                    ) {
                        Text(
                            text = landmarkData.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                        Text(
                            text = landmarkData.subtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = landmarkData.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }

                // Close Button at Bottom-Right
                TextButton(
                    onClick = {
                        showMapCard = false
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = "Close",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(id = R.color.mexgreen)
                    )
                }
            }
        }
    }

    // Back Button
    FloatingActionButton(
        onClick = { navController.popBackStack() },
        containerColor = Color.White,
        shape = CircleShape,
        modifier = Modifier
            .padding(start = 16.dp, top = 62.dp)
            .size(36.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
            modifier = Modifier.size(18.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MapScreenPreview() {
    MapScreen(
        rememberNavController(),
        LandmarkData.touristic[0]
    )
}