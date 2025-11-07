package com.example.pexel.di.modules

import com.example.pexel.data.repositories.DownloadPhotoRepositoryImpl
import com.example.pexel.data.repositories.PhotoRepositoryImpl
import com.example.pexel.domain.repository.DownloadPhotoRepository
import com.example.pexel.domain.repository.PhotoRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {

    @Binds
    fun bindPhotoRepository(impl: PhotoRepositoryImpl): PhotoRepository

    @Binds
    fun bindDownloadRepository(impl: DownloadPhotoRepositoryImpl): DownloadPhotoRepository
}