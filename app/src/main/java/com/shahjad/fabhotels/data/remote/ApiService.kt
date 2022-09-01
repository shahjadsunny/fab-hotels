package com.shahjad.fabhotels.data.remote

import com.shahjad.fabhotels.data.models.login.LoginModel
import com.shahjad.fabhotels.data.remote.Urls.LOGIN
import com.shahjad.fabhotels.data.remote.Urls.NEWS_URL
import com.shahjad.fabhotels.data.models.news.NewsModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET(LOGIN)
    suspend fun login(): Response<LoginModel>

    @GET(NEWS_URL)
    suspend fun news(): Response<NewsModel>

}