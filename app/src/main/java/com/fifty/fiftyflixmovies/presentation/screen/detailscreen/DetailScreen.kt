package com.fifty.fiftyflixmovies.presentation.screen.detailscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fifty.fiftyflixmovies.presentation.screen.home.HomeViewModel
import com.fifty.fiftyflixmovies.presentation.screen.home.MovieBanner

@Composable
fun DetailScreen(
    navController: NavController,
    movieId: Int?,
    viewModel: HomeViewModel = hiltViewModel()
) {
    viewModel.getMovie(movieId ?: 0)
    val movie = viewModel.selectedMovie.observeAsState()

    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            MovieBanner(bannerMovie = movie.value)
            Column(modifier = Modifier.padding(16.dp)) {
                //Movie head.
                Text(
                    fontWeight = FontWeight.Bold,
                    text = movie.value?.title ?: "",
                    style = TextStyle(color = Color.White, fontSize = 24.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Release Date: ${movie.value?.releaseDate ?: ""}",
                    style = TextStyle(color = Color.White, fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.value?.overview ?: "",
                    style = TextStyle(color = Color.Gray)
                )
            }
        }
    }
}