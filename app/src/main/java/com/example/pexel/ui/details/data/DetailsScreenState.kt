package com.example.pexel.ui.details.data

import com.example.pexel.domain.model.Photo

data class DetailsScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val photo: Photo = Photo(),
    val lastPhotoId: Int? = null,
    val isFromNetwork: Boolean = false
)