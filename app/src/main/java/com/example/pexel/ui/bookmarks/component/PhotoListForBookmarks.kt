package com.example.pexel.ui.bookmarks.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pexel.domain.model.Photo
import kotlinx.coroutines.flow.Flow

@Composable
fun PhotoListForBookmarks(
    photoList: LazyPagingItems<Photo>,
    onDetailsClickFromBookmarks: (Int) -> Unit,
    onNavigateToHomeClick: () -> Unit,
    lazyVerticalStaggeredState: LazyStaggeredGridState
) {
    if (photoList.itemCount == 0) {
        ErrorBookmarks {
            onNavigateToHomeClick()
        }
    } else {
        LazyVerticalStaggeredGrid(
            state = lazyVerticalStaggeredState,
            modifier = Modifier.padding(start = 24.dp, end = 6.dp, top = 40.dp),
            columns = StaggeredGridCells.Fixed(2),
            content = {
                items(photoList.itemCount) { index ->
                    val photo = photoList[index] ?: return@items
                    PhotoItemForBookmarks(
                        url = photo.src.original,
                        photographer = photo.photographer,
                        onClick = onDetailsClickFromBookmarks,
                        photoId = photo.id
                    )
                }
            }
        )
    }
}