package com.fifty.fiftyflixmovies.data.remote

import com.fifty.fiftyflixmovies.BuildConfig.API_KEY
import com.fifty.fiftyflixmovies.data.remote.response.MoviesResponse
import com.fifty.fiftyflixmovies.util.Constants.API_LANGUAGE
import com.fifty.fiftyflixmovies.util.Constants.STARTING_PAGE_INDEX
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBApi {
    @GET("trending/movie/day")
    suspend fun getTrendingTodayMovies(
        @Query("page") page: Int = STARTING_PAGE_INDEX,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = API_LANGUAGE
    ): MoviesResponse
}