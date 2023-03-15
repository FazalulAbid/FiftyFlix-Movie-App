package com.fifty.fiftyflixmovies.data.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.fifty.fiftyflixmovies.data.model.Genre
import com.fifty.fiftyflixmovies.data.model.Movie

data class GenreWithMovies(
    @Embedded val genre: Genre,
    @Relation(
        parentColumn = "genreId",
        entityColumn = "movieId",
        associateBy = Junction(MovieGenreCrossRef::class)
    )
    val movies: List<Movie>
)
