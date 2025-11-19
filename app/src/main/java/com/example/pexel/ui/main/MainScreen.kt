package com.example.pexel.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.pexel.di.ViewModelFactoryState
import com.example.pexel.ui.bookmarks.BookmarksScreen
import com.example.pexel.ui.details.DetailsScreen
import com.example.pexel.ui.home.HomeScreen
import com.example.pexel.ui.navigation.AppNavGraph
import com.example.pexel.ui.navigation.BottomNavItem
import com.example.pexel.ui.navigation.NavigationState
import com.example.pexel.ui.navigation.Screen
import com.example.pexel.ui.navigation.rememberNavigationState
import com.example.pexel.ui.search.SearchScreen

@Composable
fun MainScreen(viewModelFactoryState: ViewModelFactoryState) {
    val navigationState = rememberNavigationState()
    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        bottomBar = {
            val currentRoute = currentRoute(navigationState.navHostController)
            if (currentRoute != Screen.DetailsScreen.route) {
                BottomNavBar(
                    navigationState = navigationState
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = { Snackbar(snackbarData = it, modifier = Modifier.padding(8.dp)) }
            )
        }
    ) { paddingValues ->
        AppNavGraph(
            navHostController = navigationState.navHostController,
            bookmarkScreenContent = {
                BookmarksScreen(
                    viewModelFactoryState = viewModelFactoryState,
                    onPhotoDetailsClick = { photoId ->
                        navigationState.navigateToDetails(
                            photoId = photoId, isFromBookmarks = true
                        )
                    },
                    onNavigateToHomeClick = {
                        navigationState.navigateTo(Screen.HomeScreen.route)
                    }
                )
            },
            homeScreenContent = {
                HomeScreen(
                    viewModelFactoryState = viewModelFactoryState,
                    onDetailsClickFromHome = { photoId ->
                        navigationState.navigateToDetails(
                            photoId = photoId, isFromBookmarks = false
                        )
                    }
                )
            },
            detailsScreenContent = { photo, isFromBookmarks ->
                DetailsScreen(
                    viewModelFactoryState = viewModelFactoryState,
                    photoId = photo.id,
                    isFromBookmarks = isFromBookmarks,
                    onBackPress = { navigationState.navigateBack() },
                    onNavigateBackClick = { navigationState.navigateBack() }
                )
            },
            searchScreenContent = {
                SearchScreen(
                    viewModelFactoryState = viewModelFactoryState,
                    onDetailsClickFromSearch = { photoId ->
                        navigationState.navigateToDetails(
                            photoId = photoId, isFromBookmarks = false
                        )
                    }
                )
            }
        )
    }
}

@Composable
private fun BottomNavBar(
    navigationState: NavigationState,
) {
    val navBackStackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    val items = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Bookmark)

    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
        items.forEach { item ->
            val selected = navBackStackEntry?.destination?.route == item.screen.route

            NavigationBarItem(
                selected = selected,
                onClick = { if (!selected) navigationState.navigateTo(item.screen.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = item.activeIcon),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
private fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}




