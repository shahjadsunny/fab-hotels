package com.shahjad.fabhotels.data

import com.shahjad.fabhotels.data.Result
import com.shahjad.fabhotels.data.models.news.NewsModel

interface NewsDataSource {
    suspend fun getNews():Result<NewsModel>
}