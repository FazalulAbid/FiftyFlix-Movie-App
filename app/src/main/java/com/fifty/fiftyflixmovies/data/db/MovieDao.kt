package com.fifty.fiftyflixmovies.data.db

import androidx.room.*
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.model.relations.GenreWithMovies

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovies(movies: List<Movie>)

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<Movie>

    @Transaction
    @Query("SELECT * FROM genres WHERE genreId = :genreId")
    suspend fun getMoviesOfGenre(genreId: Int): List<GenreWithMovies>
}