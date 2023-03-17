package com.fifty.fiftyflixmovies.presentation.screen.download

interface Downloader {
    fun downloadImage(imageUrl: String): Long
}