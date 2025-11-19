package com.example.pexel.ui.navigation

sealed class Screen(
    val route: String
) {

    object HomeScreen : Screen(ROUTE_HOME_SCREEN)

    object DetailsScreen : Screen(ROUTE_DETAILS_SCREEN) {
        fun getRouteWithArgs(photoId: Int, isFromBookmarks: Boolean): String {
            return "$ROUTE_FOR_ARGS/$photoId/$isFromBookmarks"
        }
    }

    object BookmarkScreen : Screen(ROUTE_BOOKMARK_SCREEN)

    object SearchScreen : Screen(ROUTE_SEARCH_SCREEN)

    companion object {
        const val KEY_PHOTO_ID = "photo_id"
        const val KEY_IS_FROM_BOOKMARKS = "is_from_bookmarks"
        const val ROUTE_HOME_SCREEN = "home_screen"
        const val ROUTE_DETAILS_SCREEN = "details_screen/{$KEY_PHOTO_ID}/{${KEY_IS_FROM_BOOKMARKS}}"
        const val ROUTE_BOOKMARK_SCREEN = "bookmark_screen"
        const val ROUTE_FOR_ARGS = "details_screen"
        const val ROUTE_SEARCH_SCREEN = "search_screen"
    }

}