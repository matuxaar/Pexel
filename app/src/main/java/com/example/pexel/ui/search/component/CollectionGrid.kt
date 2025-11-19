package com.example.pexel.ui.search.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pexel.domain.model.Collection


@Composable
fun CollectionGrid(
    collections: List<Collection>,
    onCollectionClicked: (String) -> Unit
) {
    val gridState = rememberLazyStaggeredGridState()
    LazyVerticalStaggeredGrid(
        state = gridState,
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier.padding(8.dp),
        content = {
            items(collections.size) { index ->
                val collection = collections[index]
                CollectionItem(
                    title = collection.title,
                    onCollectionClicked = onCollectionClicked
                )
            }
        }
    )
}