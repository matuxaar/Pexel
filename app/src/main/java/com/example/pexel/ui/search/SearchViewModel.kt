package com.example.pexel.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pexel.domain.model.Photo
import com.example.pexel.domain.repository.PhotoRepository
import com.example.pexel.ui.search.data.SearchScreenAction
import com.example.pexel.ui.search.data.SearchScreenState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow(SearchScreenState())
    val searchScreenState = _searchScreenState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    val searchPhotoPagingFlow: Flow<PagingData<Photo>> = _searchQuery
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                photoRepository.getSearchPhotos(query = query)
            }
        }
        .cachedIn(viewModelScope)

    fun handleAction(action: SearchScreenAction) {
        when (action) {
            is SearchScreenAction.Init -> loadCollections()
            is SearchScreenAction.Reload -> reload()
            is SearchScreenAction.Search -> searchPhotos(action.query)
            is SearchScreenAction.ErrorSearch -> setError()
        }
    }

    private fun loadCollections() {
        viewModelScope.launch {
            _searchScreenState.update { it.copy(isLoading = true, isError = false) }
            val result = photoRepository.getCollections()
            result.fold(
                onSuccess = { collections ->
                    _searchScreenState.update {
                        it.copy(
                            collections = collections,
                            isLoading = false,
                            isError = false
                        )
                    }
                },
                onFailure = {
                    _searchScreenState.update { it.copy(isError = true, isLoading = false) }
                }
            )
        }
    }

    private fun searchPhotos(query: String) {
        _searchQuery.value = query
        _searchScreenState.update {
            it.copy(searchQuery = query, isLoading = false, isError = false)
        }

    }

    private fun reload() {
        val currentQuery = _searchScreenState.value.searchQuery
        if (currentQuery.isNotBlank()) {
            searchPhotos(currentQuery)
        } else {
            loadCollections()
        }
    }

    private fun setError() {
        _searchScreenState.update { it.copy(isError = true, isLoading = false) }
    }

}