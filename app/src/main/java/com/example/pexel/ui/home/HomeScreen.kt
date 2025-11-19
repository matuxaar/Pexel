package com.example.pexel.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pexel.di.ViewModelFactoryState
import com.example.pexel.di.daggerViewModel
import com.example.pexel.ui.component.HorizontalProgressBar
import com.example.pexel.ui.home.component.ErrorHome
import com.example.pexel.ui.home.component.PhotoList

@Composable
fun HomeScreen(
    viewModelFactoryState: ViewModelFactoryState,
    onDetailsClickFromHome: (Int) -> Unit
) {
    val homeViewModel: HomeViewModel =
        daggerViewModel<HomeViewModel>(viewModelFactoryState.viewModelFactory)
    val lazyGridState = rememberLazyStaggeredGridState()

    HomeScreenContent(
        onDetailsClickFromHome = onDetailsClickFromHome,
        homeViewModel = homeViewModel,
        lazyGridState = lazyGridState
    )
}

@Composable
private fun HomeScreenContent(
    onDetailsClickFromHome: (Int) -> Unit,
    homeViewModel: HomeViewModel,
    lazyGridState: LazyStaggeredGridState
) {
    val photoItems = homeViewModel.photoPagingFlow.collectAsLazyPagingItems()
    val isRefreshing = photoItems.loadState.refresh is LoadState.Loading
    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = { photoItems.refresh() },
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            photoItems.loadState.refresh is LoadState.Error -> {
                ErrorHome(
                    onTryAgainClick = { homeViewModel.reload() }
                )
            }

            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    if (photoItems.loadState.append is LoadState.Loading) {
                        HorizontalProgressBar()
                    }
                    PhotoList(
                        photoList = photoItems,
                        lazyVerticalStaggeredState = lazyGridState,
                        onDetailsClickFromHome = onDetailsClickFromHome
                    )
                }
            }
        }
    }
}