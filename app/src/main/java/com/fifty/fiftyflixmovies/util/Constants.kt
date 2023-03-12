package com.fifty.fiftyflixmovies.util

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val STARTING_PAGE_INDEX = 0
    const val IMAGE_BASE_UR = "https://image.tmdb.org/t/p/w500/"
    const val API_LANGUAGE = "en"
    const val DATABASE_NAME = "favorites_database"
    const val TABLE_NAME = "favorites_table"
    const val SPLASH_SCREEN_DURATION = 2000L

    // Database table names.
    const val MOVIES_TABLE = "movies"
    const val GENRES_TABLE = "genres"
    const val MOVIE_CATEGORIES_TABLE = "movie_categories"

    // Movies categories.
    const val TRENDING_MOVIES_ID = 1001
    const val POPULAR_MOVIES_ID = 1002
    const val UPCOMING_MOVIES_ID = 1003
    const val NOW_PLAYING_MOVIES_ID = 1004
    const val TOP_RATED_MOVIES_ID = 1005

}