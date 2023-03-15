package com.fifty.fiftyflixmovies.data.model

import com.fifty.fiftyflixmovies.R


sealed class BottomNavItem(var title: String, var icon: Int) {
    object Home : BottomNavItem(
        title = "Home",
        icon = R.drawable.ic_home,
    )
}