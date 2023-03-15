package com.fifty.fiftyflixmovies.presentation.screen.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fifty.fiftyflixmovies.presentation.home.ui.theme.primaryGray

@Composable
fun StandardToolbar(
    modifier: Modifier = Modifier,
    showBackArrow: Boolean = false,
//    navActions: @Composable RowScope.() -> Unit = {},
    title: @Composable () -> Unit = {}
) {
    TopAppBar(
        title = title,
        navigationIcon = if (showBackArrow) {
            {
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = primaryGray
                    )
                }
            }
        } else null,
//        actions = navActions,
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 5.dp
    )
}