package com.fifty.fiftyflixmovies.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fifty.fiftyflixmovies.util.Constants.MOVIES_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = MOVIES_TABLE)
data class Movie(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = false)
    val movieId: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String
)