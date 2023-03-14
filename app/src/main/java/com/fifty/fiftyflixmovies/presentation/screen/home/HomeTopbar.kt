package com.fifty.fiftyflixmovies.presentation.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fifty.fiftyflixmovies.R

@Composable
fun HomeTopBar(dashboardContentScrollState: ScrollState) {
    Column {
        // Hiding App bar with netflix logo.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .animateContentSize(animationSpec = tween(durationMillis = PRIMARY_APPBAR_SCROLL_ANIMATION_DURATION))
                .height(height = if (dashboardContentScrollState.isScrolled) 0.dp else PRIMARY_TOP_BAR_HEIGHT)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.fifty_logo),
                        contentDescription = "App logo",
                        modifier = Modifier
                            .size(width = 90.dp, height = 90.dp)
                            .padding(6.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        // Screencast button.
                        IconButton(onClick = {}) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_screen_cast),
                                colorFilter = ColorFilter.tint(Color.White),
                                contentDescription = "Screencast"
                            )
                        }
                        // Profile Avatar.
                        IconButton(onClick = {}) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_person),
                                colorFilter = ColorFilter.tint(Color.White),
                                contentDescription = "User profile"
                            )
                        }
                    }
                }
            }
        }
        // Non hiding app bar with lists.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = if (dashboardContentScrollState.isScrolled) colorResource(id = R.color.transparent_black)
                    else Color.Transparent
                )
                .height(SECONDARY_TOP_BAR_HEIGHT)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically
            ) {
                TopAppBarListButton(buttonText = "Movies")
                TopAppBarListButton(buttonText = "TV Shows")
            }
        }
    }
}

@Composable
fun RowScope.TopAppBarListButton(buttonText: String) {
    androidx.compose.material3.Button(
        onClick = {},
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent, contentColor = Color.White
        )
    ) {
        androidx.compose.material3.Text(
            text = buttonText,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

const val PRIMARY_APPBAR_SCROLL_ANIMATION_DURATION = 500
val PRIMARY_TOP_BAR_HEIGHT = 70.dp
val SECONDARY_TOP_BAR_HEIGHT = 56.dp
val BOTTOM_NAVIGATION_BAR = 65.dp
val ScrollState.isScrolled: Boolean
    get() = value > PRIMARY_TOP_BAR_HEIGHT.value