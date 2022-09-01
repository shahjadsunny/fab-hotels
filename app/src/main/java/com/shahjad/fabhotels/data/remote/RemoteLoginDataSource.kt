package com.shahjad.fabhotels.data.remote

import com.shahjad.fabhotels.data.LoginDataSource
import com.shahjad.fabhotels.data.Result
import com.shahjad.fabhotels.data.models.login.LoginModel
import javax.inject.Inject

class RemoteLoginDataSource@Inject constructor(private val apiService: ApiService) :LoginDataSource {
    override suspend fun login(): Result<LoginModel> {
        val response =  apiService.login()

        try {
            if (response.isSuccessful)
                response.body()?.let {
                    return  Result.Success(it)
                }

            return  Result.Error(Exception("Users Not Found"))
        }catch (e:Exception){
            return  Result.Error(Exception(e.toString()))
        }
    }
}