package com.example.pexel.di.modules

import android.content.Context
import androidx.room.Room
import com.example.pexel.data.database.AppDataBase
import com.example.pexel.data.database.PhotoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(context: Context): AppDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDataBase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providePhotoDao(db: AppDataBase): PhotoDao = db.getPhotoDao()

    companion object {
        private const val DATABASE_NAME = "database_name"
    }
}