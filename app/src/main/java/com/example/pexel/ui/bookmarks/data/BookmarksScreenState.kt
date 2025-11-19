package com.example.pexel.ui.bookmarks.data

import androidx.paging.PagingData
import com.example.pexel.domain.model.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class BookmarksScreenState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val photoList: Flow<PagingData<Photo>> = emptyFlow()
)
