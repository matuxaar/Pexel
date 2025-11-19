package com.example.pexel.domain.use_case

import com.example.pexel.domain.model.Photo
import com.example.pexel.domain.repository.PhotoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPhotoDetailsUseCase @Inject constructor(
    private val photoRepository: PhotoRepository
) {
    operator fun invoke(photoId: Int, fromNetwork: Boolean): Flow<Photo?> {
        return if (fromNetwork) {
            flow {
                val result = photoRepository.getPhotoRemoteById(photoId)
                emit(
                    result.getOrNull()?.let { photo ->
                        val liked = photoRepository.isPhotoBookmarked(photo.id)
                        photo.copy(liked = liked)
                    }
                )
            }
        } else {
            flow {
                val photo = photoRepository.getPhotoById(photoId)
                emit(photo?.copy(liked = photoRepository.isPhotoBookmarked(photo?.id ?: -1)))
            }
        }
    }
}