package com.fifty.fiftyflixmovies.data.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.model.MovieList
import com.fifty.fiftyflixmovies.data.paging.*
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieCacheDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieLocalDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieRemoteDataSource
import com.fifty.fiftyflixmovies.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieRemoveDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieCacheDataSource: MovieCacheDataSource
) : MovieRepository {

    override suspend fun getMovies(): List<Movie> {
        return getMoviesFromCache()
    }

    override suspend fun updateMovies(): List<Movie> {
        val newListOfMovie = getMoviesFromApi()
        movieLocalDataSource.clearAll()
        movieLocalDataSource.saveMoviesToDB(newListOfMovie)
        movieCacheDataSource.saveMoviesToCache(newListOfMovie)
        return newListOfMovie
    }

    private suspend fun getMoviesFromApi(): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            val response = movieRemoveDataSource.getMovies()
            val body: MovieList? = response.body()

            if (body != null)
                movieList = body.movies

        } catch (exception: Exception) {
            Timber.tag(TAG).i("getMoviesFromApi: %s", exception.message)
        }

        return movieList
    }

    private suspend fun getMoviesFromDB(): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            movieList = movieLocalDataSource.getMoviesFromDB()
        } catch (exception: Exception) {
            Timber.tag(TAG).i("getMoviesFromDB: %s", exception.message)
        }

        if (movieList.isNotEmpty())
            return movieList
        else {
            movieList = getMoviesFromApi()
            movieLocalDataSource.saveMoviesToDB(movieList)
        }

        return movieList
    }

    private suspend fun getMoviesFromCache(): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            movieList = movieCacheDataSource.getMoviesFromCache()
        } catch (exception: Exception) {
            Timber.tag(TAG).i("getMoviesFromCache: %s", exception.message)
        }

        if (movieList.isNotEmpty())
            return movieList
        else {
            movieList = getMoviesFromDB()
            movieCacheDataSource.saveMoviesToCache(movieList)
        }

        return movieList
    }

    companion object {
        private const val TAG = "MoviesRepositoryImpl"
    }
}