package com.example.pexel.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pexel.data.mappers.PhotoMapper
import com.example.pexel.data.network.PhotoService
import com.example.pexel.domain.model.Photo
import javax.inject.Inject

class PhotoPagingSource @Inject constructor(
    private val photoService: PhotoService,
    private val photoMapper: PhotoMapper
) : PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val closestPage = state.closestPageToPosition(anchorPosition)
            closestPage?.prevKey?.plus(1) ?: closestPage?.nextKey?.minus(1)
        }
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        val perPage = PAGE_SIZE

        return try {
            val response = photoService.getCurated(
                mapOf("page" to page, "per_page" to perPage)
            )

            val photos = response.photos.map { photoMapper(it) }

            LoadResult.Page(
                data = photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (photos.size < perPage) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}