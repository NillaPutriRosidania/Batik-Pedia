package com.nilla.batikpedia.response

data class UploadResponse<T>(
    val status: String,
    val message: String,
    val data: T?
)

data class UploadData(
    val sejarah: String,
    val asal: String,
    val nama: String,
    val imageUrl: String
)