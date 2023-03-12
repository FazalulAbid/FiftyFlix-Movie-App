package com.fifty.fiftyflixmovies.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.screen.common.MovieItem
import com.fifty.fiftyflixmovies.util.Constants

@Composable
fun HorizontalMoviesList(moviesList: List<Movie>) {
    Box(content = {
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(content = {

                items(1) { film ->
                    MovieItem(
                        modifier = Modifier
                            .height(200.dp)
                            .width(140.dp)
                            .clickable {
                                //navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
                            },
                        imageUrl = ""
                        //imageUrl = "${Constants.IMAGE_BASE_UR}/${film?.posterPath}"
                    )
                }

//                if (moviesList.loadState.append == LoadState.Loading) {
//                    item {
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .wrapContentWidth(Alignment.CenterHorizontally)
//                        )
//                    }
//                }
            }
            )

//            moviesList.apply {
//                when (loadState.refresh) {
//                    is LoadState.Loading -> {
//                        CircularProgressIndicator(
//                            modifier = Modifier,
//                            color = primaryPink,
//                            strokeWidth = 2.dp
//                        )
//                    }
//                    is LoadState.Error -> {
//                        val e = moviesList.loadState.refresh as LoadState.Error
//                        Text(
//                            text = when (e.error) {
//                                is HttpException -> {
//                                    "Oops, something went wrong!"
//                                }
//                                is IOException -> {
//                                    "Couldn't reach server, check your internet connection!"
//                                }
//                                else -> {
//                                    "Unknown error occurred"
//                                }
//                            },
//                            textAlign = TextAlign.Center,
//                            color = primaryPink
//                        )
//                    }
//                    else -> {
//
//                    }
//                }
//            }
        }
    })
}