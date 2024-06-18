package com.nilla.batikpedia.response

data class NewsResponse(
    val status: String,
    val data: List<NewsItem>
)

data class NewsItem(
    val id: String,
    val timestamp: String,
    val imageUrl: String,
    val judul: String
)