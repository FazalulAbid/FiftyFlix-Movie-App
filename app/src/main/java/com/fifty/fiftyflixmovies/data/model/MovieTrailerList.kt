package com.fifty.fiftyflixmovies.data.model

import com.google.gson.annotations.SerializedName

data class MovieTrailerList(
    @SerializedName("results")
    val results: List<MovieTrailer>
)
