package com.example.pexel.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo_table WHERE id = :id")
    suspend fun getPhotoById(id: Int): PhotoEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBookmarks(photoEntity: PhotoEntity)

    @Query("DELETE FROM photo_table WHERE id = :photoId")
    suspend fun removeFromBookmark(photoId: Int)

    @Query("DELETE FROM photo_table")
    suspend fun clearAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<PhotoEntity>)

    @Query("SELECT * FROM photo_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getAllPhotoFromDb(limit: Int, offset: Int): List<PhotoEntity>

    @Query("SELECT * FROM photo_table WHERE id = :id LIMIT 1")
    suspend fun isPhotoInBookmarks(id: Int): PhotoEntity?
}