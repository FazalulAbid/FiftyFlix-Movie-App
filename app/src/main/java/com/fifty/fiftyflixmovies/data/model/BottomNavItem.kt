package com.fifty.fiftyflixmovies.data.model

import com.fifty.fiftyflixmovies.R
import com.fifty.fiftyflixmovies.screen.destinations.Destination
import com.fifty.fiftyflixmovies.screen.destinations.HomeScreenDestination


sealed class BottomNavItem(var title: String, var icon: Int, var destination: Destination) {
    object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
        destination = HomeScreenDestination
    )
}