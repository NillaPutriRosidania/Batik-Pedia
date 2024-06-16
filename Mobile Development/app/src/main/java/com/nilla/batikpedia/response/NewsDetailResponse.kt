package com.nilla.batikpedia.response

data class NewsDetailResponse(
    val status: String,
    val data: NewsDetailItem
)

data class NewsDetailItem(
    val id: String,
    val image: String,
    val body: String,
    val judul: String,
    val timestamp: String
)