package com.fifty.fiftyflixmovies.presentation.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fifty.fiftyflixmovies.presentation.Navigation
import com.fifty.fiftyflixmovies.presentation.home.ui.theme.FiftyFlixMoviesTheme
import com.fifty.fiftyflixmovies.presentation.screen.common.StandardScaffold
import com.fifty.fiftyflixmovies.presentation.screen.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FiftyFlixMoviesTheme {
                Navigation()
            }
        }
    }
}