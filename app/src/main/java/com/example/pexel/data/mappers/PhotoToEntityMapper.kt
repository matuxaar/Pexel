package com.example.pexel.data.mappers

import com.example.pexel.data.database.PhotoEntity
import com.example.pexel.domain.model.Photo
import javax.inject.Inject

class PhotoToEntityMapper @Inject constructor(
    private val srcToEntityMapper: SrcToEntityMapper
) {

    operator fun invoke (photo: Photo): PhotoEntity = with(photo) {
        return PhotoEntity(
            id = id,
            width = width,
            height = height,
            url = url,
            photographer = photographer,
            liked = liked,
            src = srcToEntityMapper(src)
        )
    }

}