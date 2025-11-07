package com.example.pexel.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo_table WHERE id = :id")
    suspend fun getPhotoById(id: Int): PhotoEntity

    @Query("SELECT * FROM photo_table ORDER BY id ASC LIMIT :limit OFFSET :offset")
    fun getAllPhotoFromDb(limit: Int, offset: Int): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM photo_table")
    fun getAllPhotos(): Flow<List<PhotoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToBookmarks(photoEntity: PhotoEntity)

    @Query("DELETE FROM photo_table WHERE id = :photoId AND liked = :liked")
    suspend fun removeFromBookmark(photoId: Int, liked: Boolean)

    @Query("DELETE FROM photo_table")
    suspend fun clearAll()

//    @Query(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(photos: List<PhotoEntity>)

    @Query("SELECT * FROM photo_table ORDER BY id ASC")
    fun getAllPhotoPagingSource(): PagingSource<Int, PhotoEntity>

}