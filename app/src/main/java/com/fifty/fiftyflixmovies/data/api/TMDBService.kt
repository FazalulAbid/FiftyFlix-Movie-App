package com.fifty.fiftyflixmovies.data.api

import com.fifty.fiftyflixmovies.data.model.GenreList
import com.fifty.fiftyflixmovies.data.model.MovieList
import com.fifty.fiftyflixmovies.data.model.MovieResponse
import com.fifty.fiftyflixmovies.util.Constants.API_LANGUAGE
import com.fifty.fiftyflixmovies.util.Constants.STARTING_PAGE_INDEX
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    @GET("trending/movie/day")
    suspend fun getTrendingTodayMovies(
        @Query("api_key")
        apiKey: String
    ): Response<MovieList>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String
    ): Response<MovieList>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        apiKey: String
    ): Response<MovieList>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String
    ): Response<MovieList>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key")
        apiKey: String
    ): Response<MovieList>

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en"
    ): GenreList

    @GET("genre/tv/list")
    suspend fun getTvSeriesGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en"
    ): GenreList

}