package com.fifty.fiftyflixmovies.screen.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.compose.collectAsLazyPagingItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val dashboardContentScrollState = rememberScrollState()
    val trendingMovies = viewModel.trendingMovies.value.collectAsLazyPagingItems()
    viewModel.fetchBannerMovie()
    val bannerMovie = viewModel.bannerMovie.collectAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(Modifier.verticalScroll(dashboardContentScrollState)) {
            // Banner Image.
            BannerImage(bannerMovie)
            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
                    .height(300.dp)
            ) {
                item(content = {
                    Text(
                        text = "Trending today",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    //Spacer(modifier = Modifier.height(8.dp))
                })

                // Trending movies today.
                item {
                    TrendingToday(trendingMovies = trendingMovies)
                }
            }
        }

        // Custom home top-bar.
        HomeTopBar(dashboardContentScrollState)
    }
}