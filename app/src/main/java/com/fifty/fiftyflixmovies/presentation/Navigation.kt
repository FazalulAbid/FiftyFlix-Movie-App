package com.fifty.fiftyflixmovies.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fifty.fiftyflixmovies.presentation.screen.home.HomeScreen
import com.fifty.fiftyflixmovies.presentation.screen.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
    }
}
