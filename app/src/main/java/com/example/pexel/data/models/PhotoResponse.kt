package com.example.pexel.data.models

import com.squareup.moshi.Json

data class PhotoResponse(
    @param:Json(name = "id") val id: Int?,
    @param:Json(name = "width") val width: Int?,
    @param:Json(name = "height") val height: Int?,
    @param:Json(name = "url") val url: String?,
    @param:Json(name = "photographer") val photographer: String?,
    @param:Json(name = "src") val src: SrcResponse?,
    @param:Json(name = "alt") val alt: String?,
    @param:Json(name = "liked") val liked: Boolean?
)
