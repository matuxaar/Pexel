package com.example.pexel.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pexel.domain.model.Photo
import com.example.pexel.domain.repository.PhotoRepository
import com.example.pexel.ui.home.data.HomeScreenAction
import com.example.pexel.ui.home.data.HomeScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = _homeScreenState.asStateFlow()

    val photoPagingFlow: Flow<PagingData<Photo>> =
        photoRepository.subscribeToPhotos().cachedIn(viewModelScope)

    fun handleAction(action: HomeScreenAction) {
        when(action) {
            is HomeScreenAction.Init -> init()
            is HomeScreenAction.Reload -> reload()
            is HomeScreenAction.ErrorHome -> setError()
        }
    }

    private fun init() {
        _homeScreenState.update {
            it.copy(isLoading = true, isError = false)
        }
    }

    fun reload() {
        _homeScreenState.update { it.copy(isLoading = true, isError = false) }
    }

    private fun setError() {
        _homeScreenState.update { it.copy(isError = true, isLoading = false) }
    }


}