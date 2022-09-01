package com.shahjad.fabhotels.data.models.news

data class NewsModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)