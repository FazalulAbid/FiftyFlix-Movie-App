package com.fifty.fiftyflixmovies.data.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.model.MovieCategory

data class MovieCategoryWithMovies(
    @Embedded val movieCategory: MovieCategory,
    @Relation(
        parentColumn = "movieCategoryId",
        entityColumn = "movieId",
        associateBy = Junction(MovieCategoryMovieCrossRef::class)
    )
    val movies: List<Movie>
)
