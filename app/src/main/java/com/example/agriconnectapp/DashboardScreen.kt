package com.example.agriconnectapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Dashboard",
            fontSize = 28.sp,
            color = Color(0xFF4CAF50), // Green color
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Example Data (To be replaced with real data)
        Text("🌾 Market Price: ₹2000/quintal", fontSize = 18.sp)
        Text("⛅ Weather: 32°C, Sunny", fontSize = 18.sp)
        Text("🌱 Crops Uploaded: 5", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Go to Profile Button
        Button(
            onClick = { navController.navigate("profile") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Go to Profile", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(navController = NavController(LocalContext.current))
}
