package com.fifty.fiftyflixmovies.data.repository.movie.datasource

import com.fifty.fiftyflixmovies.data.model.Movie

interface MovieLocalDataSource {
    suspend fun getMoviesFromDB(movieCategoryId: Int): List<Movie>
    suspend fun saveMoviesToDB(movies: List<Movie>, movieCategoryId: Int)
    suspend fun clearAll()
}