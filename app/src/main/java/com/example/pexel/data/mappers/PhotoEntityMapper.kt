package com.example.pexel.data.mappers

import com.example.pexel.data.database.PhotoEntity
import com.example.pexel.domain.model.Photo
import javax.inject.Inject

class PhotoEntityMapper @Inject constructor(
    private val srcEntityMapper: SrcEntityMapper
) {

    operator fun invoke (photoEntity: PhotoEntity): Photo = with(photoEntity) {
        return Photo(
            id = id,
            width = width,
            height = height,
            url = url,
            photographer = photographer,
            liked = liked,
            src = srcEntityMapper(src)
        )
    }
}