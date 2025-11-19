package com.example.pexel.di.modules

import androidx.lifecycle.ViewModel
import com.example.pexel.di.viewmodels.ViewModelKey
import com.example.pexel.ui.bookmarks.BookmarksViewModel
import com.example.pexel.ui.details.DetailsViewModel
import com.example.pexel.ui.home.HomeViewModel
import com.example.pexel.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarksViewModel::class)
    fun bindBookmarksViewModel(viewModel: BookmarksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel
}