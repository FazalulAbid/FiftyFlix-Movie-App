package com.fifty.fiftyflixmovies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fifty.fiftyflixmovies.data.paging.*
import com.fifty.fiftyflixmovies.data.remote.TMDBApi
import com.fifty.fiftyflixmovies.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: TMDBApi) {

    suspend fun getBannerMovie(): Movie {
        val response = api.getTrendingTodayMovies(1)
        val size = response.searches.size
        return response.searches[size - 1]
    }

    fun getTrendingMoviesThisWeek(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                TrendingMoviesSource(api)
            }
        ).flow
    }

    fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                PopularMovieSource(api)
            }
        ).flow
    }

    fun getUpcomingMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                UpcomingMoviesSource(api)
            }
        ).flow
    }

    fun nowPlayingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                NowPlayingMoviesSource(api)
            }
        ).flow
    }

    fun topRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                TopRatedMoviesSource(api)
            }
        ).flow
    }
}