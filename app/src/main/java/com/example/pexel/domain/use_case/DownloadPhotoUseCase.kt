package com.example.pexel.domain.use_case

import com.example.pexel.domain.repository.DownloadPhotoRepository
import javax.inject.Inject

class DownloadPhotoUseCase @Inject constructor(
    private val downloadPhotoRepository: DownloadPhotoRepository
) {
    suspend fun invoke(imageUrl: String, photoId: Int) {
        downloadPhotoRepository.downloadPhotoToDevice(imageUrl, photoId)
    }
}