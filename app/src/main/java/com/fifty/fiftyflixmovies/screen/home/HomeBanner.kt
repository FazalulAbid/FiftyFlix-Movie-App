package com.fifty.fiftyflixmovies.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.fifty.fiftyflixmovies.R
import com.fifty.fiftyflixmovies.model.Movie
import com.fifty.fiftyflixmovies.util.Constants

@Composable
fun BannerImage(bannerMovie: Movie?) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(600.dp)
    ) {
        // Banner Image.
        if (bannerMovie != null) {
            val imageUrl = "${Constants.IMAGE_BASE_UR}/${bannerMovie.posterPath}"
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = rememberImagePainter(
                    data = imageUrl, builder = {
                        placeholder(R.drawable.fifty_f_logo)
                        crossfade(true)
                    }
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = R.drawable.interstellar_movie_vertical),
                contentDescription = "",
                contentScale = ContentScale.Crop
            )
        }
        // Banner black gradient top and bottom.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.0f to Color.Black, 0.5f to Color.Transparent, 0.99f to Color.Black
                    )
                )
        )
        // Banner buttons.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(shape = CircleShape)
                        .background(Color.White.copy(0.2f)),
                    contentAlignment = Alignment.Center,

                    ) {
                    // Play button.
                    IconButton(
                        onClick = {},
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_play_solid_round),
                            contentDescription = stringResource(id = R.string.play),
                            tint = Color.White,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BannerVerticalContentButton(buttonText: String, painter: Painter, contentDescription: String) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                colorFilter = ColorFilter.tint(Color.White)
            )
            Text(text = buttonText)
        }
    }
}

@Composable
fun BannerHorizontalContentButton(
    buttonText: String, painter: Painter, contentDescription: String
) {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.Black,
            backgroundColor = Color.White,
        ),
        shape = RoundedCornerShape(8.dp),
        contentPadding = PaddingValues(start = 8.dp, end = 16.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(Color.Black)
        )
        Text(
            text = buttonText, fontWeight = FontWeight.Bold
        )
    }
}