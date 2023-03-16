package com.fifty.fiftyflixmovies.presentation

sealed class Screen(val route: String) {
    object SplashScreen : Screen("splash_screen")
    object HomeScreen : Screen("home_screen")
    object DetailScreen : Screen("detail_screen")
    object DownloadScreen : Screen("download_screen")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
