package com.example.pexel.ui.navigation

sealed class ScreenGraph(val route: String) {
    object Main : ScreenGraph(MAIN_GRAPH)
    object Bookmarks : ScreenGraph(BOOKMARK_GRAPH)
    object Search : ScreenGraph(SEARCH_GRAPH)

    companion object {
        private const val MAIN_GRAPH = "main_graph"
        private const val BOOKMARK_GRAPH = "bookmarks_graph"
        private const val SEARCH_GRAPH = "search_graph"
    }
}