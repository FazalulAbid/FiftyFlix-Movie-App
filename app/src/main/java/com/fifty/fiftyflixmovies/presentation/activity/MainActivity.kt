package com.fifty.fiftyflixmovies.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fifty.fiftyflixmovies.presentation.Navigation
import com.fifty.fiftyflixmovies.presentation.activity.ui.theme.FiftyFlixMoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiftyFlixMoviesTheme {
                //Navigation
                Navigation()
            }
        }
    }
}