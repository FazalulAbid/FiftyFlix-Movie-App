package com.fifty.fiftyflixmovies.data.repository.download.datasource

import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.data.model.Thumbnail

interface ThumbnailLocalDataSource {
    suspend fun getThumbnails(): List<Thumbnail>
    suspend fun saveThumbnailToDB(thumbnail: Thumbnail)
}