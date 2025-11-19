package com.example.pexel.ui.home.data

sealed class HomeScreenAction {
    data object Init: HomeScreenAction()
    data object Reload: HomeScreenAction()
    data object ErrorHome: HomeScreenAction()
}