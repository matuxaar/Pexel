package com.example.pexel.di.modules

import com.example.pexel.data.network.PhotoService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton
import kotlin.jvm.java

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun get(): Retrofit {
        val interceptor = TokenInterceptor()
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun getPhotoService(retrofit: Retrofit): PhotoService {
        return retrofit.create(PhotoService::class.java)
    }

    class TokenInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val authenticatedRequest = request.newBuilder()
                .header("Authorization", TOKEN_KEY)
                .build()
            return chain.proceed(authenticatedRequest)
        }
    }

    companion object {
        private const val TOKEN_KEY = "MayRwbOE6KNliitAUjms98EzqQ2pAYpI8XoGl8IgcnJWNiqxkRHImc5w"
        private const val BASE_URL = "https://api.pexels.com/v1/"
    }
}