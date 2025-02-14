package com.example.agriconnectapp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        // Title
        Text(
            text = "Profile",
            fontSize = 28.sp,
            color = Color(0xFF4CAF50),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Display User Info
        Text("👤 Name: ${user?.displayName ?: "N/A"}", fontSize = 18.sp)
        Text("📧 Email: ${user?.email ?: "N/A"}", fontSize = 18.sp)

        Spacer(modifier = Modifier.height(16.dp))

        // Logout Button
        Button(
            onClick = {
                auth.signOut()
                Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show()
                navController.navigate("login")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = NavController(LocalContext.current))
}
