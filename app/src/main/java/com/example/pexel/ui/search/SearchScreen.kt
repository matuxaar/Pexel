package com.example.pexel.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pexel.di.ViewModelFactoryState
import com.example.pexel.di.daggerViewModel
import com.example.pexel.domain.model.Collection
import com.example.pexel.domain.model.Photo
import com.example.pexel.ui.component.HorizontalProgressBar
import com.example.pexel.ui.search.component.CollectionGrid
import com.example.pexel.ui.search.component.EmptySearchResultScreen
import com.example.pexel.ui.search.component.PexelSearchBar
import com.example.pexel.ui.search.component.PhotoListForSearch
import com.example.pexel.ui.search.component.SearchErrorScreen
import com.example.pexel.ui.search.data.SearchScreenAction
import com.example.pexel.ui.search.data.SearchScreenState

@Composable
fun SearchScreen(
    viewModelFactoryState: ViewModelFactoryState,
    onDetailsClickFromSearch: (Int) -> Unit
) {
    val searchViewModel: SearchViewModel =
        daggerViewModel<SearchViewModel>(viewModelFactoryState.viewModelFactory)

    val state by searchViewModel.searchScreenState.collectAsState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val photos = searchViewModel.searchPhotoPagingFlow.collectAsLazyPagingItems()

    val handleAction: (SearchScreenAction) -> Unit = {
        searchViewModel.handleAction(it)
    }

    LaunchedEffect(Unit) {
        handleAction(SearchScreenAction.Init)
    }

    SearchScreenContent(
        collections = state.collections,
        onDetailsClickFromSearch = onDetailsClickFromSearch,
        lazyStaggeredGridState = lazyStaggeredGridState,
        handleAction = handleAction,
        searchScreenState = state,
        photos = photos
    )

}

@Composable
private fun SearchScreenContent(
    collections: List<Collection>,
    onDetailsClickFromSearch: (Int) -> Unit,
    lazyStaggeredGridState: LazyStaggeredGridState,
    handleAction: (SearchScreenAction) -> Unit,
    searchScreenState: SearchScreenState,
    photos: LazyPagingItems<Photo>
) {
    val textFieldState = remember(searchScreenState.searchQuery) {
        TextFieldState(searchScreenState.searchQuery)
    }

    Column {
        if (searchScreenState.searchQuery.isNotBlank()) {
            PexelSearchBar(
                textFieldState = textFieldState,
                onSearch = { query -> handleAction(SearchScreenAction.Search(query)) }
            )

            SearchResultContent(
                photos = photos,
                lazyStaggeredGridState = lazyStaggeredGridState,
                onDetailsClickFromSearch = onDetailsClickFromSearch,
                handleAction = handleAction
            )
        } else {
            SearchContent(
                searchScreenState = searchScreenState,
                collections = collections,
                handleAction = handleAction,
                textFieldState = textFieldState
            )
        }
    }
}

@Composable
private fun SearchContent(
    searchScreenState: SearchScreenState,
    collections: List<Collection>,
    handleAction: (SearchScreenAction) -> Unit,
    textFieldState: TextFieldState
) {
    when {
        searchScreenState.isLoading -> {
            HorizontalProgressBar()
        }

        searchScreenState.isError || collections.isEmpty() -> {
            SearchErrorScreen { handleAction(SearchScreenAction.Reload) }
        }

        else -> {
            SuccessSearchContent(
                textFieldState = textFieldState,
                handleAction = handleAction,
                collections = collections
            )
        }
    }
}

@Composable
private fun SuccessSearchContent(
    textFieldState: TextFieldState,
    handleAction: (SearchScreenAction) -> Unit,
    collections: List<Collection>
) {
    PexelSearchBar(
        textFieldState = textFieldState,
        onSearch = { query -> handleAction(SearchScreenAction.Search(query)) }
    )

    CollectionGrid(
        collections = collections,
        onCollectionClicked = { title ->
            handleAction(SearchScreenAction.Search(title))
        }
    )
}

@Composable
private fun SearchResultContent(
    photos: LazyPagingItems<Photo>,
    lazyStaggeredGridState: LazyStaggeredGridState,
    handleAction: (SearchScreenAction) -> Unit,
    onDetailsClickFromSearch: (Int) -> Unit
) {
    val refresh = photos.loadState.refresh
    val append = photos.loadState.append

    when (refresh) {
        is LoadState.Error -> {
            SearchErrorScreen {
                handleAction(SearchScreenAction.Reload)
            }
        }

        is LoadState.NotLoading if photos.itemCount == 0
                && append.endOfPaginationReached -> {
            EmptySearchResultScreen {
                handleAction(SearchScreenAction.Reload)
            }
        }

        else -> {
            PhotoListForSearch(
                photoList = photos,
                lazyVerticalStaggeredState = lazyStaggeredGridState,
                onDetailsClickFromSearch = onDetailsClickFromSearch
            )
        }
    }
}
