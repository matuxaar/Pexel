package com.example.pexel.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pexel.data.mappers.OneCollectionMapper
import com.example.pexel.data.mappers.PhotoEntityMapper
import com.example.pexel.data.mappers.PhotoMapper
import com.example.pexel.data.mappers.PhotoToEntityMapper
import com.example.pexel.data.network.PhotoService
import com.example.pexel.data.paging.BookmarksPhotoPagingSource
import com.example.pexel.data.paging.PhotoPagingSource
import com.example.pexel.data.paging.SearchPhotosPagingSource
import com.example.pexel.data.source.DataBaseSource
import com.example.pexel.domain.model.Collection
import com.example.pexel.domain.model.Photo
import com.example.pexel.domain.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val photoService: PhotoService,
    private val dataBaseSource: DataBaseSource,
    private val photoMapper: PhotoMapper,
    private val photoToEntityMapper: PhotoToEntityMapper,
    private val photoEntityMapper: PhotoEntityMapper,
    private val oneCollectionMapper: OneCollectionMapper,
) : PhotoRepository {

    override fun getSearchPhotos(query: String): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchPhotosPagingSource(
                    photoService = photoService,
                    query = query,
                    photoMapper = photoMapper
                )
            }
        ).flow

    override suspend fun getCollections(): Result<List<Collection>> =
        withContext(Dispatchers.IO) {
            val response = photoService.getFeaturedCollections(
                mapOf()
            )
            val collections = response.collections.map { oneCollectionMapper(it) }
            Result.success(collections)
        }

    override suspend fun addToBookmarks(photo: Photo) = withContext(Dispatchers.IO) {
        val entity = photoToEntityMapper(photo)
        dataBaseSource.addToBookmarks(entity)
    }

    override suspend fun removeFromBookmarks(photoId: Int) = withContext(Dispatchers.IO) {
        dataBaseSource.removeFromBookmark(photoId)
    }

    override fun subscribeToPhotos(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = {
                PhotoPagingSource(
                    photoService = photoService,
                    photoMapper = photoMapper
                )
            }
        ).flow

    override fun subscribeToPhotosFromBookmarks(): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                BookmarksPhotoPagingSource(
                    dataBaseSource, photoEntityMapper
                )
            },
            initialKey = 0
        ).flow.flowOn(Dispatchers.IO)


    override suspend fun getPhotoById(id: Int): Photo? {
        return try {
            val entity = dataBaseSource.getPhotoById(id)
            photoEntityMapper(entity)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun isPhotoBookmarked(photoId: Int): Boolean =
        withContext(Dispatchers.IO) {
            try {
                val entity = dataBaseSource.isPhotoInBookmarks(photoId)
                entity != null
            } catch (e: Exception) {
                false
            }
        }

    override suspend fun getPhotoRemoteById(id: Int): Result<Photo> =
        withContext(Dispatchers.IO) {
            runCatching {
                val response = photoService.getPhoto(id)
                photoMapper(response)
            }
        }

    companion object {
        private const val PAGE_SIZE = 20
    }
}