package com.example.pexel.data.network


import com.example.pexel.data.models.CollectionsResponse
import com.example.pexel.data.models.CuratedResponse
import com.example.pexel.data.models.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface PhotoService {

    @GET("search")
    suspend fun search(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("query") query: String
    ): CuratedResponse

    @GET("curated")
    suspend fun getCurated(
        @QueryMap params: Map<String, Int>
    ): CuratedResponse

    @GET("photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: Int
    ): PhotoResponse

    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @QueryMap params: Map<String, Int>
    ): CollectionsResponse

}