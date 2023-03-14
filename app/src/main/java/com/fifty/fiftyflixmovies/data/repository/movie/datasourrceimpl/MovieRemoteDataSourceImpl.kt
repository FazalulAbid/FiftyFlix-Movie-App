package com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl

import com.fifty.fiftyflixmovies.data.api.TMDBService
import com.fifty.fiftyflixmovies.data.model.MovieList
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieRemoteDataSource
import com.fifty.fiftyflixmovies.util.Constants.NOW_PLAYING_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.POPULAR_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.TOP_RATED_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.TRENDING_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.UPCOMING_MOVIES_ID
import retrofit2.Response

class MovieRemoteDataSourceImpl(
    private val tmdbService: TMDBService,
    private val apiKey: String
) : MovieRemoteDataSource {
    override suspend fun getMoviesOfCategory(movieCategoryId: Int): Response<MovieList> {
        val response: Response<MovieList> = when (movieCategoryId) {
            TRENDING_MOVIES_ID ->
                tmdbService.getTrendingTodayMovies(apiKey = apiKey)
            POPULAR_MOVIES_ID ->
                tmdbService.getPopularMovies(apiKey = apiKey)
            UPCOMING_MOVIES_ID ->
                tmdbService.getUpcomingMovies(apiKey = apiKey)
            NOW_PLAYING_MOVIES_ID ->
                tmdbService.getNowPlayingMovies(apiKey = apiKey)
            TOP_RATED_MOVIES_ID ->
                tmdbService.getTopRatedMovies(apiKey = apiKey)
            else ->
                tmdbService.getPopularMovies(apiKey = apiKey)
        }
        return response
    }
}