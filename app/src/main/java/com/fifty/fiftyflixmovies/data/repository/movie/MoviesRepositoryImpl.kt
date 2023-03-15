package com.fifty.fiftyflixmovies.data.repository.movie

import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.model.MovieList
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieCacheDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieLocalDataSource
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieRemoteDataSource
import com.fifty.fiftyflixmovies.data.sharedpreference.SharedPreferenceHelper
import com.fifty.fiftyflixmovies.domain.repository.MovieRepository
import timber.log.Timber
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieRemoveDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieCacheDataSource: MovieCacheDataSource,
    private val sharedPreferenceHelper: SharedPreferenceHelper
) : MovieRepository {

    override suspend fun getMovie(movieId: Int): Movie =
        movieLocalDataSource.getMovie(movieId)

    override suspend fun getMoviesOfCategory(movieCategoryId: Int): List<Movie> {
        return getMoviesFromCache(movieCategoryId)
    }

    override suspend fun clearAllMoviesFromDB() {
        movieLocalDataSource.clearAll()
    }

    private suspend fun getMoviesFromApi(movieCategoryId: Int): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            val response = movieRemoveDataSource.getMoviesOfCategory(movieCategoryId)
            val body: MovieList? = response.body()

            if (body != null)
                movieList = body.movies

            //Update last fetched time.
            setLastFetchedTime(System.currentTimeMillis())

        } catch (exception: Exception) {
            Timber.tag(TAG).i("getMoviesFromApi: %s", exception.message)
        }

        return movieList
    }

    private suspend fun getMoviesFromDB(movieCategoryId: Int): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            movieList = movieLocalDataSource.getMoviesFromDB(movieCategoryId)
        } catch (exception: Exception) {
            Timber.tag(TAG).i("getMoviesFromDB: %s", exception.message)
        }

        if (movieList.isNotEmpty())
            return movieList
        else {
            movieList = getMoviesFromApi(movieCategoryId)
            movieLocalDataSource.saveMoviesToDB(movieList, movieCategoryId)
        }

        return movieList
    }

    private suspend fun getMoviesFromCache(movieCategoryId: Int): List<Movie> {
        lateinit var movieList: List<Movie>

        try {
            movieList = movieCacheDataSource.getMoviesFromCache(movieCategoryId)
        } catch (exception: Exception) {
            Timber.tag(TAG).i("getMoviesFromCache: %s", exception.message)
        }

        if (movieList.isNotEmpty())
            return movieList
        else {
            movieList = getMoviesFromDB(movieCategoryId)
            movieCacheDataSource.saveMoviesToCache(movieList, movieCategoryId)
        }

        return movieList
    }

    override fun getLastFetchedTime(): Long =
        sharedPreferenceHelper.getLastFetchedTime()

    private fun setLastFetchedTime(currentTime: Long) =
        sharedPreferenceHelper.setLastFetchedTime(currentTime)

    companion object {
        private const val TAG = "MoviesRepositoryImpl"
    }
}