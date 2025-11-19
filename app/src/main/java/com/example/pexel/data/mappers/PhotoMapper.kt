package com.example.pexel.data.mappers

import com.example.pexel.data.models.PhotoResponse
import com.example.pexel.domain.model.Photo
import com.example.pexel.domain.model.Src
import javax.inject.Inject

class PhotoMapper @Inject constructor() {

    operator fun invoke(photoResponse: PhotoResponse): Photo = with(photoResponse) {
        return Photo(
            id = id ?: -1,
            width = width ?: 0,
            height = height ?: 0,
            url = url.orEmpty(),
            photographer = photographer.orEmpty(),
            src = src?.let { SrcResponseMapper()(it) } ?: Src(),
            alt = alt.orEmpty()
        )
    }

}