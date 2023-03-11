package com.fifty.fiftyflixmovies.data.repository

import com.fifty.fiftyflixmovies.data.api.TMDBService
import com.fifty.fiftyflixmovies.data.model.GenreList
import com.fifty.fiftyflixmovies.util.Resource
import timber.log.Timber
import javax.inject.Inject

class GenresRepository @Inject constructor(private val api: TMDBService) {
    suspend fun getMovieGenres(): Resource<GenreList> {
        val response = try {
            api.getMovieGenres()
        } catch (e: Exception) {
            return Resource.Error("Unknown error occurred")
        }
        Timber.d("Movies genres: $response")
        return Resource.Success(response)
    }
}