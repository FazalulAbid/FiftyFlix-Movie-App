package com.fifty.fiftyflixmovies.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fifty.fiftyflixmovies.data.paging.TrendingMoviesSource
import com.fifty.fiftyflixmovies.data.remote.TMDBApi
import com.fifty.fiftyflixmovies.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val api: TMDBApi) {

    fun getTrendingMoviesThisWeek(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 27),
            pagingSourceFactory = {
                TrendingMoviesSource(api)
            }
        ).flow
    }

    suspend fun getBannerMovie(): Movie {
        val response = api.getTrendingTodayMovies(1)
        return response.searches.random()
    }

}