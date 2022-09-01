package com.shahjad.fabhotels.data

import com.shahjad.fabhotels.data.models.login.LoginModel

interface LoginRepository {
   suspend fun login(): Result<LoginModel>
}