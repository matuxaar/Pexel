package com.example.pexel.domain.repository

import androidx.paging.PagingData
import com.example.pexel.domain.model.Collection
import com.example.pexel.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getCuratedPhotos(page: Int): Result<List<Photo>>

    suspend fun getSearchPhotos(page: Int, query: String): Result<List<Photo>>

    suspend fun getCollections(): Result<List<Collection>>

    suspend fun addToBookmarks(photo: Photo)

    suspend fun removeFromBookmarks(photoId: Int, liked: Boolean)

    fun subscribeToPhotos(): Flow<PagingData<Photo>>

    fun subscribeToPhoto(id: Int): Flow<Photo?>

    fun getPhoto(id: Int): Flow<Photo>
}