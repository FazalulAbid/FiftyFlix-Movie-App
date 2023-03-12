package com.fifty.fiftyflixmovies.data.model.relations

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "movieCategoryId"])
data class MovieCategoryMovieCrossRef(
    val movieId: Int,
    val movieCategoryId: Int
)