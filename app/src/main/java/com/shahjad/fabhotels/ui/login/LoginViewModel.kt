package com.shahjad.fabhotels.ui.login

import android.content.SharedPreferences
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahjad.fabhotels.data.LoginRepository
import com.shahjad.fabhotels.data.Result
import com.shahjad.fabhotels.data.local.AppSharedPreference
import com.shahjad.fabhotels.data.models.login.LoginModel
import com.shahjad.fabhotels.util.Event
import com.shahjad.fabhotels.util.MyUtility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val appSharedPreference: AppSharedPreference
) : ViewModel() {
    val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email
    private val _isEmailValid = MutableLiveData<Int>()

    val isEmailValid: LiveData<Int> = _isEmailValid
//    val isEmailValid : LiveData<Int> = _email.map {
//                 println(isEmailValid(it))
//                if (isEmailValid(it))
//                    View.INVISIBLE
//                else
//                    View.VISIBLE
//      }

    val password = MutableLiveData<String>()
//    val password : LiveData<String> = _password

    private val _isPasswordValid = MutableLiveData<Int>()
    val isPasswordValid: LiveData<Int> = _isPasswordValid

//    val isPasswordValid : LiveData<Int> = _password.map {
//        println(isEmailValid(it))
//        if (it.length>5)
//            View.INVISIBLE
//        else
//            View.VISIBLE
//    }

    private val _success = MutableLiveData<Event<LoginModel?>>()
    val success: LiveData<Event<LoginModel?>> = _success

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    private val _progressBar = MutableLiveData<Int>()
    val progressBar: LiveData<Int> = _progressBar

    private val _isSubmitEnable = MutableLiveData<Boolean>()
    val isSubmitEnable: LiveData<Boolean> = _isSubmitEnable

    private fun isEmailValid(email: String?): Boolean {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }


    init {
        _isEmailValid.value = View.INVISIBLE
        _isPasswordValid.value = View.INVISIBLE
        _progressBar.value = View.GONE
        _isSubmitEnable.value = true
    }

    fun submit(view: View) {
        if (isEmailValid(email.value)) {
            _isEmailValid.value = View.INVISIBLE
            password.value?.length?.let {
                if (it > 6) {
                    loginAuth()
                    _isPasswordValid.value = View.INVISIBLE
                    return
                } else {
                    _isPasswordValid.value = View.VISIBLE
                    return
                }
            }
            _isPasswordValid.value = View.VISIBLE
            return

        } else {
            _isEmailValid.value = View.VISIBLE
        }

    }

    private fun loginAuth() {
        _progressBar.value = View.VISIBLE
        _isSubmitEnable.value = false
        Log.i(TAG, "loginAPiCall::userName:${email.value}, password:${password.value}")
        viewModelScope.launch {
            kotlin.runCatching {
                val response = loginRepository.login()
                response

            }.onSuccess { response ->
                updateUi(response)
                _progressBar.value = View.GONE
                _isSubmitEnable.value = true
            }.onFailure {
                _msg.value = it.message.toString()
                _progressBar.value = View.GONE
                _isSubmitEnable.value = true
            }
        }
    }

    private fun updateUi(response: Result<LoginModel>) {
        when (response) {
            is Result.Success -> {
                appSharedPreference.setUserToken(response.data.token)
                appSharedPreference.setUserName(response.data.full_name)
                _success.value = Event(response.data)
            }
            is Result.Error -> {
                _msg.value = response.exception.message.toString()
            }
        }

    }


    companion object {
        private const val TAG = "LoginViewModel"
    }

}