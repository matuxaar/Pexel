package com.example.pexel.domain.repository

import androidx.paging.PagingData
import com.example.pexel.domain.model.Collection
import com.example.pexel.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun getSearchPhotos(query: String): Flow<PagingData<Photo>>

    fun subscribeToPhotos(): Flow<PagingData<Photo>>

    fun subscribeToPhotosFromBookmarks(): Flow<PagingData<Photo>>

    suspend fun getCollections(): Result<List<Collection>>

    suspend fun addToBookmarks(photo: Photo)

    suspend fun removeFromBookmarks(photoId: Int)

    suspend fun getPhotoById(id: Int): Photo?

    suspend fun isPhotoBookmarked(photoId: Int): Boolean

    suspend fun getPhotoRemoteById(id: Int): Result<Photo>

}