package com.fifty.fiftyflixmovies.domain.repository

import com.fifty.fiftyflixmovies.data.model.Movie

interface MovieRepository {
    suspend fun getMovie(movieId: Int): Movie
    suspend fun getMoviesOfCategory(movieCategoryId: Int): List<Movie>?
    suspend fun clearAllMoviesFromDB()
    fun getLastFetchedTime(): Long?
}