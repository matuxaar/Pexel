package com.example.pexel.ui.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pexel.di.ViewModelFactoryState
import com.example.pexel.di.daggerViewModel
import com.example.pexel.domain.model.Photo
import com.example.pexel.ui.bookmarks.component.ErrorBookmarks
import com.example.pexel.ui.bookmarks.component.PhotoListForBookmarks
import com.example.pexel.ui.bookmarks.component.TopBar
import com.example.pexel.ui.bookmarks.data.BookmarksScreenAction
import com.example.pexel.ui.bookmarks.data.BookmarksScreenState
import com.example.pexel.ui.component.HorizontalProgressBar

@Composable
fun BookmarksScreen(
    viewModelFactoryState: ViewModelFactoryState,
    onPhotoDetailsClick: (Int) -> Unit,
    onNavigateToHomeClick: () -> Unit
) {
    val bookmarksViewModel =
        daggerViewModel<BookmarksViewModel>(factory = viewModelFactoryState.viewModelFactory)
    val handleAction: (BookmarksScreenAction) -> Unit = {
        bookmarksViewModel.handleAction(it)
    }
    LaunchedEffect(Unit) {
        handleAction(BookmarksScreenAction.Init)
    }
    val bookmarksScreenState by bookmarksViewModel.bookmarksScreenState.collectAsState()
    val lazyStaggeredGridState = rememberLazyStaggeredGridState()
    val photoList = bookmarksViewModel.bookmarksFlow.collectAsLazyPagingItems()

    BookmarksScreenContent(
        onPhotoDetailsClick = onPhotoDetailsClick,
        onNavigateToHomeClick = onNavigateToHomeClick,
        bookmarksScreenState = bookmarksScreenState,
        lazyStaggeredGridState = lazyStaggeredGridState,
        photoList = photoList
    )
}

@Composable
private fun BookmarksScreenContent(
    onPhotoDetailsClick: (Int) -> Unit,
    onNavigateToHomeClick: () -> Unit,
    bookmarksScreenState: BookmarksScreenState,
    lazyStaggeredGridState: LazyStaggeredGridState,
    photoList: LazyPagingItems<Photo>
) {
    val isLoading = bookmarksScreenState.isLoading
    val isError = bookmarksScreenState.isError
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar()
        when {
            isLoading -> HorizontalProgressBar()
            isError -> ErrorBookmarks { onNavigateToHomeClick() }
            else -> PhotoListForBookmarks(
                photoList = photoList,
                onDetailsClickFromBookmarks = onPhotoDetailsClick,
                onNavigateToHomeClick = onNavigateToHomeClick,
                lazyVerticalStaggeredState = lazyStaggeredGridState
            )
        }

    }
}