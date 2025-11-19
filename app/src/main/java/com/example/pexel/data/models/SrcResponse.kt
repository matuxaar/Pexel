package com.example.pexel.data.models

import com.squareup.moshi.Json

data class SrcResponse(
    @param:Json(name = "landscape") val landscape: String? = null,
    @param:Json(name = "large") val large: String? = null,
    @param:Json(name = "large2x") val large2x: String? = null,
    @param:Json(name = "medium") val medium: String? = null,
    @param:Json(name = "original") val original: String? = null,
    @param:Json(name = "portrait") val portrait: String? = null,
    @param:Json(name = "small") val small: String? = null,
    @param:Json(name = "tiny") val tiny: String? = null
)
