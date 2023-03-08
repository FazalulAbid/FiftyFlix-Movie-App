package com.fifty.fiftyflixmovies.screen.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.fifty.fiftyflixmovies.App
import com.fifty.fiftyflixmovies.model.Movie
import com.fifty.fiftyflixmovies.screen.common.MovieItem
import com.fifty.fiftyflixmovies.ui.theme.primaryPink
import com.fifty.fiftyflixmovies.util.Constants
import retrofit2.HttpException
import java.io.IOException

@Composable
fun TrendingToday(trendingMovies: LazyPagingItems<Movie>) {
    Box(content = {
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            LazyRow(content = {

                items(trendingMovies) { film ->
                    MovieItem(
                        modifier = Modifier
                            .height(200.dp)
                            .width(140.dp)
                            .clickable {
                                //navigator.navigate(MovieDetailsScreenDestination(film?.id!!))
                            },
                        imageUrl = "${Constants.IMAGE_BASE_UR}/${film?.posterPath}"
                    )
                }

                if (trendingMovies.loadState.append == LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
            )

            trendingMovies.apply {
                loadState
                when (loadState.refresh) {
                    is LoadState.Loading -> {
                        CircularProgressIndicator(
                            modifier = Modifier,
                            color = primaryPink,
                            strokeWidth = 2.dp
                        )
                    }
                    is LoadState.Error -> {
                        val e = trendingMovies.loadState.refresh as LoadState.Error
                        Text(
                            text = when (e.error) {
                                is HttpException -> {
                                    "Oops, something went wrong!"
                                }
                                is IOException -> {
                                    "Couldn't reach server, check your internet connection!"
                                }
                                else -> {
                                    "Unknown error occurred"
                                }
                            },
                            textAlign = TextAlign.Center,
                            color = primaryPink
                        )
                    }
                    else -> {

                    }
                }
            }
        }
    })
}