package com.fifty.fiftyflixmovies.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fifty.fiftyflixmovies.data.model.Genre
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.model.relations.MovieGenreCrossRef


@Database(
    entities = [
        Movie::class,
        Genre::class,
        MovieGenreCrossRef::class
    ],
    version = 1, exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao

    companion object {
        @Volatile
        private var INSTANCE: TMDBDatabase? = null

        fun getInstance(context: Context): TMDBDatabase {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    TMDBDatabase::class.java,
                    "tmdb_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}