package com.fifty.fiftyflixmovies.data.db

import androidx.room.*
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.model.relations.GenreWithMovies
import com.fifty.fiftyflixmovies.data.model.relations.MovieCategoryMovieCrossRef
import com.fifty.fiftyflixmovies.data.model.relations.MovieCategoryWithMovies

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieCategoryMovieCrossRef(crossRefs: List<MovieCategoryMovieCrossRef>)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movie_categories WHERE movieCategoryId = :movieCategoryId")
    suspend fun getMoviesOfCategory(movieCategoryId: Int): List<MovieCategoryWithMovies>

    @Query("SELECT * FROM movies WHERE movieId = :movieId")
    suspend fun getMovie(movieId: Int): Movie

    @Transaction
    @Query("SELECT * FROM genres WHERE genreId = :genreId")
    suspend fun getMoviesOfGenre(genreId: Int): List<GenreWithMovies>
}