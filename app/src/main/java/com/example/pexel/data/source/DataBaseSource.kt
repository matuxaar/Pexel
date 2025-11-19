package com.example.pexel.data.source

import com.example.pexel.data.database.PhotoDao
import com.example.pexel.data.database.PhotoEntity
import javax.inject.Inject

class DataBaseSource @Inject constructor(
    private val photoDao: PhotoDao
) {

    suspend fun addToBookmarks(photoEntity: PhotoEntity) =
        photoDao.addToBookmarks(photoEntity)

    suspend fun getPhotoById(id: Int): PhotoEntity =
        photoDao.getPhotoById(id)

    suspend fun removeFromBookmark(id: Int) =
        photoDao.removeFromBookmark(id)

    suspend fun insertAll(photos: List<PhotoEntity>) =
        photoDao.insertAll(photos)


    suspend fun clearAll() =
        photoDao.clearAll()


    suspend fun getAllPhotosFromDb(limit: Int, offset: Int) =
        photoDao.getAllPhotoFromDb(limit, offset)

    suspend fun isPhotoInBookmarks(photoId: Int): PhotoEntity? =
        photoDao.isPhotoInBookmarks(photoId)
}