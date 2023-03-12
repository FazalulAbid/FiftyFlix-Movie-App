package com.fifty.fiftyflixmovies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fifty.fiftyflixmovies.util.Constants.MOVIE_CATEGORIES_TABLE

@Entity(tableName = MOVIE_CATEGORIES_TABLE)
data class MovieCategory(
    @PrimaryKey(autoGenerate = false)
    var movieCategoryId: Int,
    var movieCategory: String
)
