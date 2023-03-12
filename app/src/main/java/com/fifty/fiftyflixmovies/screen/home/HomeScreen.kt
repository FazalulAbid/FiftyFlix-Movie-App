package com.fifty.fiftyflixmovies.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.fifty.fiftyflixmovies.data.model.Movie
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val movies = listOf(
        Movie(
            1,
            "Avengers",
            "Its an Overview",
            1.00,
            "Its poster path",
            "Its Release Date",
            "Its Title"
        ),
        Movie(
            2,
            "Avengers Endgame",
            "Its an Overview",
            1.00,
            "Its poster path",
            "Its Release Date",
            "Its Title"
        )
    )

    viewModel.saveToMoviesDatabase(movies)

    viewModel.fetchBannerMovie()
    val bannerMovie = viewModel.bannerMovie.collectAsState().value
    val dashboardContentScrollState = rememberScrollState()

    val trendingMovies = viewModel.trendingMovies.value.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMovies.value.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.upcomingMovies.value.collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.nowPlayingMovies.value.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMovies.value.collectAsLazyPagingItems()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(Modifier.verticalScroll(dashboardContentScrollState)) {
            // Banner Image.
            BannerImage(bannerMovie)
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {

                // Trending movies today.
                LazyColumnMovieItem(listHead = "Trending Movies", moviesList = trendingMovies)

                // Popular Movies.
                LazyColumnMovieItem(listHead = "Popular Movies", moviesList = popularMovies)

                // Upcoming Movies.
                LazyColumnMovieItem(listHead = "Upcoming Movies", moviesList = upcomingMovies)

                // Now Playing Movies.
                LazyColumnMovieItem(listHead = "Now Playing Movies", moviesList = nowPlayingMovies)

                // Upcoming Movies.
                LazyColumnMovieItem(listHead = "Top Rated Movies", moviesList = topRatedMovies)

            }
        }

        // Custom home top-bar.
        HomeTopBar(dashboardContentScrollState)
    }
}

@Composable
fun LazyColumnMovieItem(listHead: String, moviesList: LazyPagingItems<Movie>) {
    Text(
        text = listHead,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )

    HorizontalMoviesList(moviesList)
    Spacer(modifier = Modifier.height(8.dp))
}