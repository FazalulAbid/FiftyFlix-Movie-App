package com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl

import com.fifty.fiftyflixmovies.data.db.MovieCategoryDao
import com.fifty.fiftyflixmovies.data.db.MovieDao
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.model.relations.MovieCategoryMovieCrossRef
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao,
    private val movieCategoryDao: MovieCategoryDao
) : MovieLocalDataSource {

    override suspend fun getMovie(movieId: Int): Movie =
        movieDao.getMovie(movieId)

    override suspend fun getMoviesFromDB(movieCategoryId: Int): List<Movie> {
        val movieCategoryWithMovies = movieDao.getMoviesOfCategory(movieCategoryId)
        val list = ArrayList<Movie>()
        for (item in movieCategoryWithMovies) {
            list.addAll(item.movies)
        }
        return list
    }


    override suspend fun saveMoviesToDB(movies: List<Movie>, movieCategoryId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            // Save movies to movie table.
            movieDao.saveMovies(movies)
            // Save movie id and movie category id into MovieCategoryMovieCrossRef table.
            val crossRefs = ArrayList<MovieCategoryMovieCrossRef>()
            for (movie in movies) {
                crossRefs.add(
                    MovieCategoryMovieCrossRef(movie.movieId, movieCategoryId)
                )
            }
            movieDao.saveMovieCategoryMovieCrossRef(crossRefs)
        }
    }

    override suspend fun clearAll() {
        CoroutineScope(Dispatchers.IO).launch {
            movieDao.deleteAllMovies()
            movieCategoryDao.deleteAllMovieCategoryMovieCrossRef()
        }
    }

}