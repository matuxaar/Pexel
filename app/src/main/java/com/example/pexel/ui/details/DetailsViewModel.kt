package com.example.pexel.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pexel.domain.repository.PhotoRepository
import com.example.pexel.domain.use_case.DownloadPhotoUseCase
import com.example.pexel.domain.use_case.GetPhotoDetailsUseCase
import com.example.pexel.ui.details.data.DetailsScreenAction
import com.example.pexel.ui.details.data.DetailsScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val downloadPhotoUseCase: DownloadPhotoUseCase,
    private val getPhotoDetailsUseCase: GetPhotoDetailsUseCase
) : ViewModel() {

    private val _detailsScreenState = MutableStateFlow(DetailsScreenState())
    val detailsScreenState: StateFlow<DetailsScreenState> = _detailsScreenState.asStateFlow()

    fun handleAction(action: DetailsScreenAction) {
        when (action) {
            is DetailsScreenAction.Init -> loadPhoto(action.photoId, action.isFromNetwork)
            is DetailsScreenAction.Download -> downloadPhoto(action.photoId, action.imageUrl)
            is DetailsScreenAction.Like -> toggleLike()
            is DetailsScreenAction.BackPress -> {}
        }
    }

    private fun loadPhoto(photoId: Int, isFromNetwork: Boolean) {
        viewModelScope.launch {
            _detailsScreenState.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                    lastPhotoId = photoId,
                    isFromNetwork = isFromNetwork
                )
            }
            getPhotoDetailsUseCase(photoId, isFromNetwork)
                .catch {
                    _detailsScreenState.update { it.copy(isError = true, isLoading = false) }
                }
                .collect { photo ->
                    _detailsScreenState.update {
                        if (photo != null) it.copy(photo = photo, isLoading = false)
                        else it.copy(isError = true, isLoading = false)
                    }
                }

        }
    }

    private fun downloadPhoto(photoId: Int, imageUrl: String) {
        viewModelScope.launch {
            try {
                downloadPhotoUseCase.invoke(imageUrl, photoId)
            } catch (e: Exception) {
                _detailsScreenState.update { it.copy(isError = true) }
            }
        }
    }

    private fun toggleLike() {
        val currentPhoto = _detailsScreenState.value.photo
        viewModelScope.launch {
            try {
                if (currentPhoto.liked) {
                    photoRepository.removeFromBookmarks(currentPhoto.id)
                    _detailsScreenState.update {
                        it.copy(photo = currentPhoto.copy(liked = false))
                    }
                } else {
                    photoRepository.addToBookmarks(currentPhoto)
                    _detailsScreenState.update {
                        it.copy(photo = currentPhoto.copy(liked = true))
                    }
                }
            } catch (e: Exception) {
                _detailsScreenState.update { it.copy(isError = true) }
            }
        }
    }
}