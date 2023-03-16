package com.fifty.fiftyflixmovies.data.repository.download.datasourceimpl

import com.fifty.fiftyflixmovies.data.db.ThumbnailDao
import com.fifty.fiftyflixmovies.data.model.Thumbnail
import com.fifty.fiftyflixmovies.data.repository.download.datasource.ThumbnailLocalDataSource

class ThumbnailLocalDataSourceImpl(
    private val thumbnailDao: ThumbnailDao
) : ThumbnailLocalDataSource {

    override suspend fun getThumbnails(): List<Thumbnail> =
        thumbnailDao.getThumbnails()

    override suspend fun saveThumbnailToDB(thumbnail: Thumbnail) =
        thumbnailDao.saveThumbnail(thumbnail)
}