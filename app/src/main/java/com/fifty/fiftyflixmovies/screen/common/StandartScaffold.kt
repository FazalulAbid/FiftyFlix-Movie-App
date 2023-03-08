package com.fifty.fiftyflixmovies.screen.common

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
import com.fifty.fiftyflixmovies.model.BottomNavItem
import com.fifty.fiftyflixmovies.ui.theme.primaryDark
import com.fifty.fiftyflixmovies.ui.theme.primaryGray

@Composable
fun StandardScaffold(
    navController: NavController,
    showBottomBar: Boolean = true,
    items: List<BottomNavItem> = listOf(
        BottomNavItem.Home
    ),
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {
    Scaffold(bottomBar = {
        if (showBottomBar) {
            BottomNavigation(
                backgroundColor = primaryDark,
                contentColor = Color.White,
                elevation = 5.dp
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { bottomNavItem ->
                    BottomNavigationItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = bottomNavItem.icon),
                                contentDescription = bottomNavItem.title
                            )
                        },
                        label = {
                            Text(
                                text = bottomNavItem.title,
                                fontSize = 9.sp
                            )
                        },
                        selectedContentColor = primaryDark,
                        unselectedContentColor = primaryGray,
                        alwaysShowLabel = true,
                        selected = currentDestination?.route?.contains(bottomNavItem.destination.route) == true,
                        onClick = {
                            navController.navigate(bottomNavItem.destination.route) {
                                navController.graph.startDestinationRoute?.let { screen_route ->
                                    popUpTo(screen_route) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }) { paddingValues ->
        content(paddingValues)
    }
}