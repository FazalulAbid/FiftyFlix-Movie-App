package com.fifty.fiftyflixmovies.domain.repository

import com.fifty.fiftyflixmovies.data.model.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>?
    suspend fun updateMovies(): List<Movie>?
}