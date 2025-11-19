package com.example.pexel.ui.home.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.example.pexel.domain.model.Photo
import com.example.pexel.ui.component.PhotoItem

@Composable
fun PhotoList(
    photoList: LazyPagingItems<Photo>,
    lazyVerticalStaggeredState: LazyStaggeredGridState,
    onDetailsClickFromHome: (Int) -> Unit
) {
    LazyVerticalStaggeredGrid(
        state = lazyVerticalStaggeredState,
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.padding(start = 24.dp, end = 6.dp)
    ) {
        items(photoList.itemCount) { index ->
            val photo = photoList[index] ?: return@items
            PhotoItem(
                url = photo.src.medium,
                photoId = photo.id,
                onClick = onDetailsClickFromHome
            )
        }

    }
}