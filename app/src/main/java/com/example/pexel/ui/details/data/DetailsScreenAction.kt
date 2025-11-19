package com.example.pexel.ui.details.data

sealed class DetailsScreenAction {
    data class Init(
        val photoId: Int,
        val isFromNetwork: Boolean
    ): DetailsScreenAction()
    data class Download(
        val photoId: Int,
        val imageUrl: String
    ): DetailsScreenAction()
    data object Like: DetailsScreenAction()
    data object BackPress: DetailsScreenAction()
}