package com.fifty.fiftyflixmovies.presentation.screen.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.fifty.fiftyflixmovies.data.model.BottomNavItem
import com.fifty.fiftyflixmovies.presentation.home.ui.theme.primaryDark
import com.fifty.fiftyflixmovies.presentation.home.ui.theme.primaryGray
import com.fifty.fiftyflixmovies.presentation.home.ui.theme.primaryPink

@Composable
fun StandardScaffold(
    showBottomBar: Boolean = true,
    items: List<BottomNavItem> = listOf(
        BottomNavItem.Home,
    ),
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {
    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigation(
                    backgroundColor = primaryDark,
                    contentColor = Color.White,
                    elevation = 5.dp
                ) {
                    items.forEach { item ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painterResource(id = item.icon),
                                    contentDescription = item.title
                                )
                            },
                            label = {
                                Text(
                                    text = item.title,
                                    fontSize = 9.sp
                                )
                            },
                            selectedContentColor = primaryPink,
                            unselectedContentColor = primaryGray,
                            alwaysShowLabel = true,
                            selected = true,
                            onClick = {}
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}