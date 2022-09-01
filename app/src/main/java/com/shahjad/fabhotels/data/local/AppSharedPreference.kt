package com.shahjad.fabhotels.data.local

import android.content.SharedPreferences
import com.shahjad.chatspace.util.Constants.TOKEN
import com.shahjad.chatspace.util.Constants.USER_NAME

class AppSharedPreference(private var sharedPreferences: SharedPreferences) {

    fun setUserToken(userName: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(TOKEN, userName)
        editor.apply()
    }

    fun getUserToken(): String? {
        return sharedPreferences.getString(TOKEN, "")
    }

    fun setUserName(userName: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_NAME, userName)
        editor.apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME, "")
    }

}
