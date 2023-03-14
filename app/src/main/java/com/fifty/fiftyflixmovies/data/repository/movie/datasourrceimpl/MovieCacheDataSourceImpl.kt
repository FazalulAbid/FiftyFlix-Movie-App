package com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl

import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieCacheDataSource
import com.fifty.fiftyflixmovies.util.Constants.NOW_PLAYING_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.POPULAR_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.TOP_RATED_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.TRENDING_MOVIES_ID
import com.fifty.fiftyflixmovies.util.Constants.UPCOMING_MOVIES_ID

class MovieCacheDataSourceImpl : MovieCacheDataSource {

    private var movieList = ArrayList<Movie>()
    private var trendingMoviesList = ArrayList<Movie>()
    private var popularMoviesList = ArrayList<Movie>()
    private var upcomingMoviesList = ArrayList<Movie>()
    private var nowPlayingMoviesList = ArrayList<Movie>()
    private var topRatedMoviesList = ArrayList<Movie>()

    override suspend fun getMoviesFromCache(movieCategoryId: Int): List<Movie> {
        return when (movieCategoryId) {
            TRENDING_MOVIES_ID ->
                trendingMoviesList
            POPULAR_MOVIES_ID ->
                popularMoviesList
            UPCOMING_MOVIES_ID ->
                upcomingMoviesList
            NOW_PLAYING_MOVIES_ID ->
                nowPlayingMoviesList
            TOP_RATED_MOVIES_ID ->
                topRatedMoviesList
            else ->
                popularMoviesList
        }
    }

    override suspend fun saveMoviesToCache(movies: List<Movie>, movieCategoryId: Int) {
        when (movieCategoryId) {
            TRENDING_MOVIES_ID -> {
                trendingMoviesList.clear()
                trendingMoviesList = ArrayList(movies)
            }
            POPULAR_MOVIES_ID -> {
                popularMoviesList.clear()
                popularMoviesList = ArrayList(movies)
            }
            UPCOMING_MOVIES_ID -> {
                upcomingMoviesList.clear()
                upcomingMoviesList = ArrayList(movies)
            }
            NOW_PLAYING_MOVIES_ID -> {
                nowPlayingMoviesList.clear()
                nowPlayingMoviesList = ArrayList(movies)
            }
            TOP_RATED_MOVIES_ID -> {
                topRatedMoviesList.clear()
                topRatedMoviesList = ArrayList(movies)
            }
        }
    }
}