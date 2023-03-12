package com.fifty.fiftyflixmovies.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fifty.fiftyflixmovies.data.model.Genre
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.model.MovieCategory
import com.fifty.fiftyflixmovies.data.model.relations.MovieCategoryMovieCrossRef
import com.fifty.fiftyflixmovies.data.model.relations.MovieGenreCrossRef

@Database(
    entities = [
        Movie::class,
        Genre::class,
        MovieGenreCrossRef::class,
        MovieCategory::class,
        MovieCategoryMovieCrossRef::class
    ],
    version = 1, exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao
    abstract fun movieCategoryDao(): MovieCategoryDao
}