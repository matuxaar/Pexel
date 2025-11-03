package com.example.pexel

import android.app.Application
import com.example.pexel.di.AppComponent
import com.example.pexel.di.DaggerAppComponent

class DaggerApp: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}