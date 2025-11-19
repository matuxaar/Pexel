package com.example.pexel.ui.bookmarks.data

sealed class BookmarksScreenAction {
    data object Init: BookmarksScreenAction()
    data object ErrorBookmarks: BookmarksScreenAction()
}