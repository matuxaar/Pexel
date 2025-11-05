package com.example.pexel.di

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pexel.MainActivity
import com.example.pexel.di.modules.DataBaseModule
import com.example.pexel.di.modules.DataModule
import com.example.pexel.di.modules.NetworkModule
import com.example.pexel.di.modules.SourceModule
import com.example.pexel.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    DataBaseModule::class,
    DataModule::class,
    SourceModule::class,
    ViewModelModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)

}

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(
    factory: ViewModelProvider.Factory,
    viewModelStoreOwner: ViewModelStoreOwner =
        checkNotNull(LocalViewModelStoreOwner.current) {
            "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
        }
): VM {
    return viewModel(
        viewModelStoreOwner = viewModelStoreOwner,
        factory = factory
    )
}

@Immutable
data class ViewModelFactoryState(
    val viewModelFactory: ViewModelProvider.Factory
)

