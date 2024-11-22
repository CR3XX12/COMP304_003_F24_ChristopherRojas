package com.group13.comp304sec003_lab04

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.group13.comp304sec003_lab04.data.Landmark
import com.group13.comp304sec003_lab04.data.LandmarkData

@Composable
fun LandmarkList(
    navController: NavHostController,
    title: String,
    landmarks: List<Landmark>,
    onItemClick: (Landmark) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.mexico_bg), // Replace with your image resource
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Foreground content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            
            Text(
                text = title.uppercase(),
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                    letterSpacing = 12.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(landmarks) { landmark ->
                    LandmarkCard(
                        landmark = landmark,
                        onItemClick = onItemClick
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

@Composable
fun LandmarkCard(landmark: Landmark, onItemClick: (Landmark) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .border(
                BorderStroke(4.dp, Color.White), // White border with 2.dp thickness
                shape = RoundedCornerShape(16.dp)
            ).clickable {
                onItemClick(landmark)
            },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Image Section
            Image(
                painter = painterResource(id = landmark.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f)
            )
            // Text Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 10.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = landmark.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = landmark.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun LandmarkHistoricPreview() {
//    LandmarkList(rememberNavController(), "Historic", LandmarkData.historic)
//}
//
//@Preview(showBackground = true)
//@Composable
//fun LandmarkParkPreview() {
//    LandmarkList(rememberNavController(), "Park", LandmarkData.park)
//}
//
//@Preview(showBackground = true)
//@Composable
//fun LandmarkMuseumPreview() {
//    LandmarkList(rememberNavController(), "Museum", LandmarkData.museums)
//}

@Preview(showBackground = true)
@Composable
fun LandmarkTouristicPreview() {
    LandmarkList(rememberNavController(), "Touristic", LandmarkData.touristic) {}
}