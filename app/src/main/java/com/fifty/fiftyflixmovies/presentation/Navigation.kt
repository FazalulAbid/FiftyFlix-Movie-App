package com.fifty.fiftyflixmovies.presentation

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.fifty.fiftyflixmovies.presentation.screen.detailscreen.DetailScreen
import com.fifty.fiftyflixmovies.presentation.screen.download.DownloadScreen
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
        composable(
            route = Screen.DetailScreen.route + "/{movieId}",
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "https://abid-coding.com/{movieId}"
                    action = Intent.ACTION_VIEW
                }
            ),
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) { entry ->
            DetailScreen(
                navController = navController,
                movieId = entry.arguments?.getInt("movieId")
            )
        }
        composable(route = Screen.DownloadScreen.route) {
            DownloadScreen(navController = navController)
        }
    }
}
