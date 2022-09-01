package com.shahjad.fabhotels.data.remote

import com.shahjad.fabhotels.data.NewsDataSource
import com.shahjad.fabhotels.data.NewsRepository
import com.shahjad.fabhotels.data.Result
import com.shahjad.fabhotels.data.models.news.NewsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultNewsRepository @Inject constructor(val newsDataSource: NewsDataSource):NewsRepository {
    override suspend fun getNews(): Result<NewsModel> = withContext(Dispatchers.IO) {
        return@withContext newsDataSource.getNews()
    }
}