package com.example.pexel.data.models

import com.squareup.moshi.Json

data class CollectionsResponse(
    @param:Json(name = "collections") val collections: List<CollectionResponse>
)
