package com.example.pexel.ui.navigation

import com.example.pexel.R

sealed class BottomNavItem(
    val screen: Screen,
    val activeIcon: Int
) {
    object Home: BottomNavItem(
        screen = Screen.HomeScreen,
        activeIcon = R.drawable.ic_home
    )
    object Bookmark: BottomNavItem(
        screen = Screen.BookmarkScreen,
        activeIcon = R.drawable.ic_like
    )
    object Search: BottomNavItem(
        screen = Screen.SearchScreen,
        activeIcon = R.drawable.ic_search_bottom_bar
    )
}