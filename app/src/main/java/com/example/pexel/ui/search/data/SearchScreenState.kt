package com.example.pexel.ui.search.data

import com.example.pexel.domain.model.Collection
import com.example.pexel.domain.model.Photo

data class SearchScreenState(
    val isError: Boolean = false,
    val isLoading: Boolean = false,
    val collections: List<Collection> = emptyList(),
    val searchQuery: String = ""
)
