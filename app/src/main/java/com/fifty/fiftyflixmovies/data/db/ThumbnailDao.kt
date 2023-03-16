package com.fifty.fiftyflixmovies.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fifty.fiftyflixmovies.data.model.Thumbnail

@Dao
interface ThumbnailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveThumbnail(thumbnail: Thumbnail)

    @Query("SELECT * FROM thumbnail")
    suspend fun getThumbnails(): List<Thumbnail>
}