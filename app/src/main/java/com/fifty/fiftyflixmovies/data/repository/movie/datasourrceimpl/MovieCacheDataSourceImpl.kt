package com.fifty.fiftyflixmovies.data.repository.movie.datasourrceimpl

import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.repository.movie.datasource.MovieCacheDataSource

class MovieCacheDataSourceImpl : MovieCacheDataSource {

    private var movieList = ArrayList<Movie>()

    override suspend fun getMoviesFromCache(): List<Movie> {
        return movieList
    }

    override suspend fun saveMoviesToCache(movies: List<Movie>) {
        movieList.clear()
        movieList = ArrayList(movies)
    }
}