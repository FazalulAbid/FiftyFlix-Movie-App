package com.fifty.fiftyflixmovies.data.repository.movie.datasource

import com.fifty.fiftyflixmovies.data.model.Movie

interface MovieLocalDataSource {
    suspend fun getMoviesFromDB(): List<Movie>
    suspend fun saveMoviesToDB(movies: List<Movie>)
    suspend fun clearAll()
}