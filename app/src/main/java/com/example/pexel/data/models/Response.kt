package com.example.pexel.data.models

import com.squareup.moshi.Json

data class Response(
    @param:Json(name = "per_page") val perPage: Int = 0,
    @param:Json(name = "next_page") val nextPage: String = "",
    @param:Json(name = "total_result") val totalResult: Int = 0,
    val photos: List<PhotoResponse> = emptyList(),
    val page: Int = 0
)
