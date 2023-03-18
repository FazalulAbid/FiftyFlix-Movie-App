package com.fifty.fiftyflixmovies.presentation.screen.download

import android.app.DownloadManager

interface Downloader {
    var downloadManager:DownloadManager
    fun downloadImage(imageUrl: String): Long
}