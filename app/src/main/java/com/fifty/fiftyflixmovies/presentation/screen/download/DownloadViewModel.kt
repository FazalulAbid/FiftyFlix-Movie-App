package com.fifty.fiftyflixmovies.presentation.screen.download

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fifty.fiftyflixmovies.data.model.Thumbnail
import com.fifty.fiftyflixmovies.domain.repository.DownloadRepository
import com.fifty.fiftyflixmovies.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    private val downloadRepository: DownloadRepository,
    private val downloader: Downloader
) : ViewModel() {

    private val _movieThumbnails = MutableLiveData<List<Thumbnail>>()
    val movieThumbnails: MutableLiveData<List<Thumbnail>> = _movieThumbnails

    init {
        getMovieThumbnails()
    }

    fun getMovieThumbnails() {
        viewModelScope.launch {
            _movieThumbnails.value = downloadRepository.getMovieThumbnails()
        }
    }

    fun uploadMovieThumbnail(imageUri: Uri, onResult: (UiState<Uri>) -> Unit) {
        onResult.invoke(UiState.Loading)
        viewModelScope.launch {
            downloadRepository.uploadMovieThumbnail(imageUri, onResult)
        }
    }

    fun downloadImage(imageUri: String) {
        Log.i("Abid", "From viewmodel")
        downloader.downloadImage(imageUri)
    }

}