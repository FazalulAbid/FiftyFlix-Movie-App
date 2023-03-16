package com.fifty.fiftyflixmovies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fifty.fiftyflixmovies.util.Constants.THUMBNAIL_TABLE

@Entity(tableName = THUMBNAIL_TABLE)
data class Thumbnail(
    @PrimaryKey(autoGenerate = true) val thumbnailId: Int? = null,
    val thumbnailUrl: String
)
