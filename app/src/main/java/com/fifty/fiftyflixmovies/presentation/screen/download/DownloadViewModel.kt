package com.fifty.fiftyflixmovies.presentation.screen.download

import android.annotation.SuppressLint
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fifty.fiftyflixmovies.data.model.Thumbnail
import com.fifty.fiftyflixmovies.domain.repository.DownloadRepository
import com.fifty.fiftyflixmovies.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(
    application: Application,
    private val downloadRepository: DownloadRepository,
    private val downloader: Downloader
) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val _movieThumbnails = MutableLiveData<List<Thumbnail>>()
    val movieThumbnails: MutableLiveData<List<Thumbnail>> = _movieThumbnails

    private val _progress = MutableLiveData<Int>()
    val progress: LiveData<Int> = _progress

    private val _isDownloadStarted = MutableLiveData<Boolean>(false)
    val isDownloadStarted: LiveData<Boolean> = _isDownloadStarted

    private val _isDownloadComplete = MutableLiveData<Boolean>(false)
    val isDownloadComplete: LiveData<Boolean> = _isDownloadComplete

    private val _downloadProgress = MutableStateFlow(0)
    val downloadProgress: StateFlow<Int> = _downloadProgress

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

    @SuppressLint("Range")
    fun downloadFile(
        url: String,
        fileName: String = System.currentTimeMillis().toString(),
        context: Context
    ) {
        _isDownloadStarted.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val downloadId = downloader.downloadImage(url)
            var isDownloading = true
            while (isDownloading) {
                val query = DownloadManager.Query().apply {
                    setFilterById(downloadId)
                }
                val cursor = downloader.downloadManager.query(query)
                if (cursor.moveToFirst()) {
                    val downloadedBytes =
                        cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                    val totalBytes =
                        cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                    val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    when (status) {
                        DownloadManager.STATUS_FAILED,
                        DownloadManager.STATUS_PAUSED,
                        DownloadManager.STATUS_SUCCESSFUL -> {
                            _progress.postValue(0)
                            isDownloading = false
                            if (status == DownloadManager.STATUS_SUCCESSFUL)
                                _isDownloadComplete.postValue(true)
                        }
                        DownloadManager.STATUS_RUNNING -> {
                            val currentDownloadValue =
                                (downloadedBytes * 100 / totalBytes).toInt()
                            if (currentDownloadValue > 0)
                                _isDownloadStarted.postValue(false)
                            _progress.postValue(currentDownloadValue)
                        }
                    }
                    cursor.close()
                }
            }
            _isDownloadComplete.postValue(false)
            _isDownloadStarted.postValue(false)
        }
    }
}