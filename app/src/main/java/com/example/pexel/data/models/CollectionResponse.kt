package com.example.pexel.data.models

import com.squareup.moshi.Json

data class CollectionResponse(
    @param:Json(name = "title") val title: String
)
