package com.example.pexel.data.models

data class PhotoResponse(
    val id: Int? = null,
    val width: Int? = null,
    val height: Int? = null,
    val url: String? = null,
    val photographer: String? = null,
    val src: SrcResponse? = null,
    val alt: String? = null
)
