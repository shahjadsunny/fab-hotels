package com.shahjad.fabhotels.data

import com.shahjad.fabhotels.data.models.news.NewsModel

interface NewsRepository {
    suspend fun getNews():Result<NewsModel>
}