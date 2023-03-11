package com.fifty.fiftyflixmovies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fifty.fiftyflixmovies.data.model.Genre
import com.fifty.fiftyflixmovies.util.Constants.GENRES_TABLE

@Dao
interface GenreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenres(genres: List<Genre>)

    @Query("DELETE FROM $GENRES_TABLE")
    suspend fun deleteAllGenres()

    @Query("SELECT * FROM $GENRES_TABLE")
    suspend fun getGenres(): List<Genre>
}