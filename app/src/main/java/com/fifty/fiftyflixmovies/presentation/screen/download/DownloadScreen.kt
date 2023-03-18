package com.fifty.fiftyflixmovies.presentation.screen.download

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.fifty.fiftyflixmovies.R
import com.fifty.fiftyflixmovies.data.model.Thumbnail
import com.fifty.fiftyflixmovies.util.UiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DownloadScreen(
    navController: NavController,
    downloadViewModel: DownloadViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val progress by downloadViewModel.progress.observeAsState(0)
    val animatedProgress by animateIntAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000)
    )
    val isDownloadStarted by downloadViewModel.isDownloadStarted.observeAsState(false)
    val isDownloadComplete by downloadViewModel.isDownloadComplete.observeAsState(false)
    val movieThumbnails = downloadViewModel.movieThumbnails.observeAsState(emptyList())
    // Create a launcher for the gallery picker
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            // Set the selected image URI in the ViewModel
            uploadMovieThumbnail(uri, downloadViewModel)
        }
    )
    Scaffold(
        backgroundColor = Color.Black,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    launcher.launch("image/*")
                },
                modifier = Modifier
                    .padding(16.dp),
                backgroundColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            modifier = Modifier.padding(
                top = 8.dp,
                bottom = 0.dp,
                start = 8.dp,
                end = 8.dp
            )
        ) {
            Text(
                text = "Thumbnails",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = 8.dp)
            )
            LazyVerticalGrid(
                cells = GridCells.Fixed(3),
                content = {
                    items(movieThumbnails.value) { thumbnail ->
                        MovieThumbnailItem(modifier = Modifier.clickable {
                            downloadViewModel.downloadFile(
                                thumbnail.thumbnailUrl,
                                context = context
                            )
                        }, thumbnail = thumbnail)
                    }
                }
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(7.dp),
                progress = animatedProgress / 100f,
                color = Color.Red
            )
            if (isDownloadStarted) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(7.dp),
                    color = Color.Red
                )
            }
            if (isDownloadComplete) {
                Toast.makeText(context, "Download Completed.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun MovieThumbnailItem(modifier: Modifier, thumbnail: Thumbnail) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(1.dp)
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberImagePainter(
                data = thumbnail.thumbnailUrl, builder = {
                    placeholder(R.drawable.fifty_f_logo)
                    crossfade(true)
                }
            ),
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            contentDescription = "Movie Thumbnail"
        )
    }
}

fun uploadMovieThumbnail(imageUri: Uri?, viewModel: DownloadViewModel) {
    if (imageUri != null) {
        viewModel.uploadMovieThumbnail(imageUri) { state ->
            when (state) {
                is UiState.Loading -> {
                    // Show progress bar
                }
                is UiState.Failure -> {

                }
                is UiState.Success -> {
                    viewModel.getMovieThumbnails()
                }
            }
        }
    }
}