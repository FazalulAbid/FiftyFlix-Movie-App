package com.fifty.fiftyflixmovies.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.fifty.fiftyflixmovies.R
import com.fifty.fiftyflixmovies.screen.destinations.HomeScreenDestination
import com.fifty.fiftyflixmovies.util.Constants.SPLASH_SCREEN_DURATION
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Destination(start = true)
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val scale = remember {
            Animatable(0f)
        }

        val overshootInterpolator = remember {
            OvershootInterpolator(2.5f)
        }

        LaunchedEffect(key1 = true) {
            withContext(Dispatchers.Main) {
                scale.animateTo(
                    targetValue = 1.5f,
                    animationSpec = tween(
                        durationMillis = 700,
                        easing = {
                            overshootInterpolator.getInterpolation(it)
                        }
                    )
                )

                delay(SPLASH_SCREEN_DURATION)
                navigator.popBackStack()
                navigator.navigate(HomeScreenDestination)
            }
        }

        Image(
            painter = painterResource(id = R.drawable.fifty_logo),
            contentDescription = "App-logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(8.dp)
        )

    }
}