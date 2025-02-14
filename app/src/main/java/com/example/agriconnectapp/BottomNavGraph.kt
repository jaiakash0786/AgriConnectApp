package com.example.agriconnectapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(navController = navController, startDestination = "dashboard", modifier = modifier) {
        composable("dashboard") { DashboardScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
    }
}
