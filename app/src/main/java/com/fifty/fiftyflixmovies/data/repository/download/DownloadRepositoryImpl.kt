package com.fifty.fiftyflixmovies.data.repository.download

import android.net.Uri
import com.fifty.fiftyflixmovies.data.model.Thumbnail
import com.fifty.fiftyflixmovies.data.repository.download.datasource.ThumbnailLocalDataSource
import com.fifty.fiftyflixmovies.domain.repository.DownloadRepository
import com.fifty.fiftyflixmovies.util.FirebaseConstants.MOVIE_THUMBNAILS_DIRECTORY
import com.fifty.fiftyflixmovies.util.UiState
import com.google.firebase.FirebaseException
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class DownloadRepositoryImpl @Inject constructor(
    private val thumbnailLocalDataSource: ThumbnailLocalDataSource,
    private val storageReference: StorageReference
) : DownloadRepository {

    override suspend fun getMovieThumbnails(): List<Thumbnail> =
        thumbnailLocalDataSource.getThumbnails()

    override suspend fun uploadMovieThumbnail(imageUri: Uri, onResult: (UiState<Uri>) -> Unit) {
        try {
            val reference = storageReference.child(MOVIE_THUMBNAILS_DIRECTORY)
                .child(System.currentTimeMillis().toString())
            reference.putFile(imageUri).addOnCompleteListener { thumbnailImageTask ->
                if (thumbnailImageTask.isSuccessful) {
                    reference.downloadUrl.addOnSuccessListener { downloadableUri ->
                        // Save download link to local database.
                        CoroutineScope(Dispatchers.IO).launch {
                            thumbnailLocalDataSource.saveThumbnailToDB(
                                Thumbnail(thumbnailUrl = downloadableUri.toString())
                            )
                            onResult.invoke(UiState.Success(downloadableUri))
                        }
                    }
                }
            }
        } catch (exception: FirebaseException) {
            onResult.invoke(UiState.Failure(exception.message))
        } catch (exception: Exception) {
            onResult.invoke(UiState.Failure(exception.message))
        }
    }
}