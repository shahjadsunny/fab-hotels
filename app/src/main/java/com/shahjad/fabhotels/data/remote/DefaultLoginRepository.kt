package com.shahjad.fabhotels.data.remote

import com.shahjad.fabhotels.data.LoginDataSource
import com.shahjad.fabhotels.data.LoginRepository
import com.shahjad.fabhotels.data.Result
import com.shahjad.fabhotels.data.models.login.LoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultLoginRepository @Inject constructor(private  val loginDataSource: LoginDataSource)
    : LoginRepository {
    override suspend fun login(): Result<LoginModel> = withContext(Dispatchers.IO) {
        return@withContext loginDataSource.login()
    }

}