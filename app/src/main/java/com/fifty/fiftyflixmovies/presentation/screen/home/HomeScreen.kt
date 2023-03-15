package com.fifty.fiftyflixmovies.presentation.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fifty.fiftyflixmovies.data.model.Movie
import com.fifty.fiftyflixmovies.presentation.screen.common.StandardScaffold

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val dashboardContentScrollState = rememberScrollState()
    val trendingMovies = viewModel.trendingMovies.observeAsState(emptyList())
    val popularMovies = viewModel.popularMovies.observeAsState(emptyList())
    val upcomingMovies = viewModel.upcomingMovies.observeAsState(emptyList())
    val nowPlayingMovies = viewModel.nowPlayingMovies.observeAsState(emptyList())
    val topRatedMovies = viewModel.topRatedMovies.observeAsState(emptyList())

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        StandardScaffold(
            showBottomBar = false
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(Modifier.verticalScroll(dashboardContentScrollState)) {
                        // Banner Image.
                        val bannerMovie = viewModel.bannerMovie.collectAsState().value
                        MovieBanner(bannerMovie)
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            // Trending movies today.
                            LazyColumnMovieItem(
                                listHead = "Trending Movies",
                                movieState = trendingMovies,
                                navController = navController
                            )
                            LazyColumnMovieItem(
                                listHead = "Popular Movies",
                                movieState = popularMovies,
                                navController = navController
                            )
                            LazyColumnMovieItem(
                                listHead = "Upcoming Movies",
                                movieState = upcomingMovies,
                                navController = navController
                            )
                            LazyColumnMovieItem(
                                listHead = "Now Playing Movies",
                                movieState = nowPlayingMovies,
                                navController = navController
                            )
                            LazyColumnMovieItem(
                                listHead = "Top Rated Movies",
                                movieState = topRatedMovies,
                                navController = navController
                            )
                        }
                    }

                    // Custom home top-bar.
                    HomeTopBar(dashboardContentScrollState)
                }
            }
        }
    }


}

@Composable
fun LazyColumnMovieItem(
    listHead: String,
    movieState: State<List<Movie>>,
    navController: NavController
) {
    Text(
        text = listHead,
        color = Color.White,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )

    HorizontalMoviesList(movieState, navController)
    Spacer(modifier = Modifier.height(8.dp))
}