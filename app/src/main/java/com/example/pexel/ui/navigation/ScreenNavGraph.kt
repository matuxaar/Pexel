package com.example.pexel.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.pexel.domain.model.Photo

fun NavGraphBuilder.screenNavGraph(
    startDestination: String,
    parentRoute: String,
    screenContent: @Composable () -> Unit,
    detailsScreenContent: @Composable (Photo, Boolean) -> Unit
) {
    navigation(
        startDestination = startDestination,
        route = parentRoute
    ) {
        composable(startDestination) {
            screenContent()
        }
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
            detailsScreenContent(Photo(id = photoId), isFromBookmarks)
        }
    }
}