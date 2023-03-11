package com.fifty.fiftyflixmovies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fifty.fiftyflixmovies.util.Constants.GENRES_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = GENRES_TABLE)
data class Genre(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val genreId: Int,
    @SerializedName("name")
    val name: String
)
