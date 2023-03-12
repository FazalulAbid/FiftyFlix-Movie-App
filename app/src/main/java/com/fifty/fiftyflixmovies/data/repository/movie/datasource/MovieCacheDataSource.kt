package com.fifty.fiftyflixmovies.data.repository.movie.datasource

import com.fifty.fiftyflixmovies.data.model.Movie

interface MovieCacheDataSource {
    suspend fun getMoviesFromCache(): List<Movie>
    suspend fun saveMoviesToCache(movies: List<Movie>)
}