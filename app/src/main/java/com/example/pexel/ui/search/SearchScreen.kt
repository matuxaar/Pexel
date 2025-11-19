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

@Composable
fun SearchScreen(
    viewModelFactoryState: ViewModelFactoryState,
    onDetailsClickFromSearch: (Int) -> Unit
) {
    val searchViewModel: SearchViewModel =
        daggerViewModel<SearchViewModel>(viewModelFactoryState.viewModelFactory)

    val state by searchViewModel.searchScreenState.collectAsState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()

    val handleAction: (SearchScreenAction) -> Unit = {
        searchViewModel.handleAction(it)
    }

    LaunchedEffect(Unit) {
        handleAction(SearchScreenAction.Init)
    }

    SearchScreenContent(
        searchViewModel = searchViewModel,
        collections = state.collections,
        onDetailsClickFromSearch = onDetailsClickFromSearch,
        lazyStaggeredGridState = lazyStaggeredGridState,
        handleAction = handleAction
    )

}

@Composable
private fun SearchScreenContent(
    searchViewModel: SearchViewModel,
    collections: List<Collection>,
    onDetailsClickFromSearch: (Int) -> Unit,
    lazyStaggeredGridState: LazyStaggeredGridState,
    handleAction: (SearchScreenAction) -> Unit,
) {
    val searchScreenState by searchViewModel.searchScreenState.collectAsState()
    val photos = searchViewModel.searchPhotoPagingFlow.collectAsLazyPagingItems()
    val textFieldState = remember(searchScreenState.searchQuery) {
        TextFieldState(searchScreenState.searchQuery)
    }
    Column {
        if (photos.loadState.append is LoadState.Loading) {
            HorizontalProgressBar()
        }
        PexelSearchBar(
            textFieldState = textFieldState,
            onSearch = { query ->
                handleAction(SearchScreenAction.Search(query))
            }
        )

        when {
            searchScreenState.searchQuery.isNotBlank() -> {
                SearchResultContent(
                    photos = photos,
                    handleAction = handleAction,
                    lazyStaggeredGridState = lazyStaggeredGridState,
                    onDetailsClickFromSearch = onDetailsClickFromSearch
                )
            }

            else -> {
                CollectionGrid(
                    collections = collections,
                    onCollectionClicked = { title ->
                        handleAction(SearchScreenAction.Search(title))
                    }
                )
            }
        }
    }

}

@Composable
private fun SearchResultContent(
    photos: LazyPagingItems<Photo>,
    handleAction: (SearchScreenAction) -> Unit,
    lazyStaggeredGridState: LazyStaggeredGridState,
    onDetailsClickFromSearch: (Int) -> Unit
) {
    val refresh = photos.loadState.refresh
    val append = photos.loadState.append

    when (refresh) {
        is LoadState.Error -> {
            SearchErrorScreen {
                handleAction(SearchScreenAction.ErrorSearch)
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
