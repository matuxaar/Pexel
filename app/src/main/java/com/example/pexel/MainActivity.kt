package com.example.pexel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.pexel.di.ViewModelFactoryState
import com.example.pexel.di.viewmodels.ViewModelFactory
import com.example.pexel.ui.main.MainScreen
import com.example.pexel.ui.theme.PexelTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (applicationContext as DaggerApp).appComponent.inject(this)
        val viewModelFactoryState = ViewModelFactoryState(factory)
        enableEdgeToEdge()
        setContent {
            PexelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModelFactoryState = viewModelFactoryState)
                }
            }
        }
    }
}
