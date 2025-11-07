package com.example.pexel.data.source

import com.example.pexel.data.database.PhotoDao
import com.example.pexel.data.database.PhotoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataBaseSource @Inject constructor(
    private val photoDao: PhotoDao
) {

    suspend fun addToBookmarks(photoEntity: PhotoEntity) = photoDao.addToBookmarks(photoEntity)

    suspend fun getPhotoById(id: Int): PhotoEntity = photoDao.getPhotoById(id)

    fun getAllPhotoFromDb(limit: Int, offset: Int): Flow<List<PhotoEntity>> =
        photoDao.getAllPhotoFromDb(limit, offset)

    fun getAllPhotos(): Flow<List<PhotoEntity>> = photoDao.getAllPhotos()

    suspend fun removeFromBookmark(id: Int, liked: Boolean) =
        photoDao.removeFromBookmark(id,liked)
}