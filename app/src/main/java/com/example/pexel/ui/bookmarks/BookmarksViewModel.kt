package com.example.pexel.ui.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pexel.domain.model.Photo
import com.example.pexel.domain.repository.PhotoRepository
import com.example.pexel.ui.bookmarks.data.BookmarksScreenAction
import com.example.pexel.ui.bookmarks.data.BookmarksScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookmarksViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {
    private val _bookmarksScreenState = MutableStateFlow(BookmarksScreenState())
    val bookmarksScreenState = _bookmarksScreenState.asStateFlow()

    val bookmarksFlow: Flow<PagingData<Photo>> = photoRepository
        .subscribeToPhotosFromBookmarks().cachedIn(viewModelScope)

    fun handleAction(action: BookmarksScreenAction) {
        when (action) {
            is BookmarksScreenAction.Init -> getLikedPhotos()
            is BookmarksScreenAction.ErrorBookmarks -> setError()
        }
    }


    private fun getLikedPhotos() {
        photoRepository.subscribeToPhotosFromBookmarks()
            .cachedIn(viewModelScope)
            .onStart {
                _bookmarksScreenState.update { it.copy(isLoading = true) }
            }.catch {
                it.printStackTrace()
            }.onEach {
                _bookmarksScreenState.update { currentState ->
                    currentState.copy(photoList = flowOf(it))
                }
                _bookmarksScreenState.update { it.copy(isLoading = false) }
            }.launchIn(viewModelScope)
    }

    private fun setError() {
        viewModelScope.launch {
            _bookmarksScreenState.update { it.copy(isError = true) }
        }
    }
}