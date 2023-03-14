package com.fifty.fiftyflixmovies.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.screen.common.MovieItem
import com.fifty.fiftyflixmovies.util.Constants.IMAGE_BASE_UR

@Composable
fun HorizontalMoviesList(movieState: State<List<Movie>>) {
    Box(content = {
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(content = {
                items(movieState.value) { movie ->
                    MovieItem(
                        modifier = Modifier
                            .height(200.dp)
                            .width(140.dp)
                            .clickable {
                                //navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
                            },
                        imageUrl = "${IMAGE_BASE_UR}/${movie.posterPath}"
                    )
                }
            }
            )
        }
    })
}