package com.fifty.fiftyflixmovies.domain.repository

import android.net.Uri
import com.fifty.fiftyflixmovies.util.UiState

interface DownloadRepository {
    suspend fun uploadMovieThumbnail(imageUri: Uri, onResult: (UiState<Uri>) -> Unit)
}