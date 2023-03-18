package com.fifty.fiftyflixmovies.presentation.screen.common

import android.content.Context
import android.widget.Toast


fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}