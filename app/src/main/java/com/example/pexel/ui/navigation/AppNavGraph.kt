package com.example.pexel.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.pexel.domain.model.Photo

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    detailsScreenContent: @Composable (Photo, Boolean) -> Unit,
    bookmarkScreenContent: @Composable () -> Unit,
    homeScreenContent: @Composable () -> Unit,
    searchScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = ScreenGraph.Main.route
    ) {
        // Main graph
        navigation(
            startDestination = Screen.HomeScreen.route,
            route = ScreenGraph.Main.route
        ) {
            composable(Screen.HomeScreen.route) { homeScreenContent() }
            composable(
                route = Screen.DetailsScreen.route,
                arguments = listOf(
                    navArgument(Screen.KEY_PHOTO_ID) { type = NavType.IntType },
                    navArgument(Screen.KEY_IS_FROM_BOOKMARKS) { type = NavType.BoolType }
                )
            ) { backStackEntry ->
                val photoId = backStackEntry.arguments?.getInt(Screen.KEY_PHOTO_ID) ?: 0
                val isFromBookmarks =
                    backStackEntry.arguments?.getBoolean(Screen.KEY_IS_FROM_BOOKMARKS) ?: false
                detailsScreenContent(Photo(photoId), isFromBookmarks)
            }
        }

        // Search graph
        screenNavGraph(
            startDestination = Screen.SearchScreen.route,
            parentRoute = ScreenGraph.Search.route,
            screenContent = searchScreenContent,
            detailsScreenContent = detailsScreenContent
        )

        // Bookmarks graph
        screenNavGraph(
            startDestination = Screen.BookmarkScreen.route,
            parentRoute = ScreenGraph.Bookmarks.route,
            screenContent = bookmarkScreenContent,
            detailsScreenContent = detailsScreenContent
        )
    }

}