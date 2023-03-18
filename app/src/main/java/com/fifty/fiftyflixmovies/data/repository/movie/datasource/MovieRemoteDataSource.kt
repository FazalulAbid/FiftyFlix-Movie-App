package com.fifty.fiftyflixmovies.data.repository.movie.datasource

import com.fifty.fiftyflixmovies.data.model.MovieList
import com.fifty.fiftyflixmovies.data.model.MovieResponse
import com.fifty.fiftyflixmovies.data.model.MovieTrailerList
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getMoviesOfCategory(movieCategoryId:Int): Response<MovieList>
    suspend fun getMovieTrailer(movieId:Int): Response<MovieTrailerList>
}