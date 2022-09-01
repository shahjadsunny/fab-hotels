package com.shahjad.fabhotels.data.remote

import com.shahjad.fabhotels.data.NewsDataSource
import com.shahjad.fabhotels.data.Result
import com.shahjad.fabhotels.data.models.news.NewsModel
import javax.inject.Inject

class RemoteNewsDataSource @Inject constructor(private val apiService: ApiService) :NewsDataSource {
    override suspend fun getNews(): Result<NewsModel> {
        val response =  apiService.news()

        try {
            if (response.isSuccessful)
                response.body()?.let {
                    return  Result.Success(it)
                }

            return  Result.Error(Exception("News Not Found"))
        }catch (e:Exception){
            return  Result.Error(Exception(e.toString()))
        }
    }
}