package com.fifty.fiftyflixmovies.presentation.screen.download

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class AndroidDownloader(
    private val context: Context
) : Downloader {
    override var downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun downloadImage(imageUrl: String): Long {
        val fileName = "${System.currentTimeMillis()}.jpg"
        val request = DownloadManager.Request(imageUrl.toUri())
            .setMimeType("image/jpeg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle(fileName)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        return downloadManager.enqueue(request)
    }
}