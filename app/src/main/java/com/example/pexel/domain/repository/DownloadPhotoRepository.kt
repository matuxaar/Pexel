package com.example.pexel.domain.repository

interface DownloadPhotoRepository {

    suspend fun downloadPhotoToDevice(imageUrl: String, photoId: Int): Long

}