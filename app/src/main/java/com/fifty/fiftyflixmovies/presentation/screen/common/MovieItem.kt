package com.fifty.fiftyflixmovies.presentation.screen.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.fifty.fiftyflixmovies.R

@Composable
fun MovieItem(modifier: Modifier, imageUrl: String) {
    Card(modifier = modifier.padding(4.dp)) {
        Image(
            painter = rememberImagePainter(
                data = imageUrl, builder = {
                    placeholder(R.drawable.fifty_f_logo)
                    crossfade(true)
                }
            ),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = "Movie Banner"
        )
    }
}