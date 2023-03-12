package com.fifty.fiftyflixmovies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fifty.fiftyflixmovies.data.model.MovieCategory
import com.fifty.fiftyflixmovies.data.model.relations.MovieCategoryWithMovies

@Dao
interface MovieCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieCategory(movieCategories: List<MovieCategory>)

    @Query("SELECT * FROM movie_categories WHERE movieCategoryId = :categoryId")
    suspend fun getMoviesOfCategory(categoryId: Int): List<MovieCategoryWithMovies>
}