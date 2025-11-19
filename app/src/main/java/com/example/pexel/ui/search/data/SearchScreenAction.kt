package com.example.pexel.ui.search.data

sealed class SearchScreenAction {
    data object Init: SearchScreenAction()
    data object Reload: SearchScreenAction()
    data class Search(val query: String): SearchScreenAction()
    data object ErrorSearch: SearchScreenAction()
}