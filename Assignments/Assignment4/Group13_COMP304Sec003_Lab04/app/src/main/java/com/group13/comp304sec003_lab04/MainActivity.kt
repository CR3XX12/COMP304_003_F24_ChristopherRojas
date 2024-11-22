package com.group13.comp304sec003_lab04

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.group13.comp304sec003_lab04.navigation.AppNavigation
import com.group13.comp304sec003_lab04.ui.theme.Group13_COMP304Sec003_Lab04Theme

class MainActivity : ComponentActivity() {
    private val requestPermissionLauncher : ActivityResultLauncher<String>
            = registerForActivityResult(ActivityResultContracts.RequestPermission())
    { isGranted ->
        if (!isGranted) {
            // Permission denied, show warning dialog
            showDialog = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Group13_COMP304Sec003_Lab04Theme {
                LocationPermissionHandler()
                AppNavigation()
            }
        }
    }

    private var showDialog by mutableStateOf(false)

    @Composable
    fun LocationPermissionHandler() {
        var hasRequestedPermission by remember { mutableStateOf(false) }

        if (showDialog) {
            WarningDialog(
                onDismiss = { showDialog = false },
                onConfirm = {
                    showDialog = false
                    // Re-request permission when user confirms
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            )
        } else if (!hasRequestedPermission) {
            // Request permission on first launch
            LaunchedEffect(Unit) {
                hasRequestedPermission = true
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    @Composable
    fun WarningDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text("Allow")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
            title = {
                Text("Location Access Required")
            },
            text = {
                Text("This app requires location access to function properly. Please grant permission.")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    LandingPreview()
}