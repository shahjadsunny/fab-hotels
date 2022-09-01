package com.shahjad.fabhotels.ui.login

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class LoginViewModel : ViewModel() {
    val _email = MutableLiveData<String>()
    val email : LiveData<String> = _email
    private val _isEmailValid = MutableLiveData<Int>()

    val isEmailValid : LiveData<Int> = _isEmailValid
//    val isEmailValid : LiveData<Int> = _email.map {
//                 println(isEmailValid(it))
//                if (isEmailValid(it))
//                    View.INVISIBLE
//                else
//                    View.VISIBLE
//      }

    val _password = MutableLiveData<String>()
    val password : LiveData<String> = _password

    private val _isPasswordValid = MutableLiveData<Int>()
    val isPasswordValid : LiveData<Int> = _isPasswordValid

//    val isPasswordValid : LiveData<Int> = _password.map {
//        println(isEmailValid(it))
//        if (it.length>5)
//            View.INVISIBLE
//        else
//            View.VISIBLE
//    }

    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg

    private val _progressBar = MutableLiveData<Int>()
    val progressBar : LiveData<Int> = _progressBar

    private val _isSubmitEnable = MutableLiveData<Boolean>()
    val isSubmitEnable : LiveData<Boolean> = _isSubmitEnable

    private fun isEmailValid(email: String?): Boolean {
         return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }


    init {
        _isEmailValid.value = View.INVISIBLE
        _isPasswordValid.value = View.INVISIBLE
        _progressBar.value = View.GONE
        _isSubmitEnable.value = true
    }

    fun submit(view: View){
        Log.i(TAG, "submit::userName:${email.value}, password:${password.value}")
        if (isEmailValid(email.value)){
            _isEmailValid.value = View.INVISIBLE
            password.value?.length?.let {
                if (it>6){
                    loginAPiCall()
                    _isPasswordValid.value = View.INVISIBLE
                    return
                }
                else{
                    _isPasswordValid.value = View.VISIBLE
                }
            }
            _isPasswordValid.value = View.VISIBLE
            return

        }else{
            _isEmailValid.value = View.VISIBLE
        }
//        _isEmailValid.value = email.value!!.isNotEmpty()
//        _msg.value = "userName:${email.value}, password:${password.value}"

    }

    private fun loginAPiCall() {
        _progressBar.value = View.VISIBLE
        _isSubmitEnable.value = false
        Log.i(TAG, "loginAPiCall::userName:${email.value}, password:${password.value}")
         viewModelScope.launch(Dispatchers.Main) {
             delay(5000)
             _progressBar.value = View.GONE
             _isSubmitEnable.value = true
         }
    }

    companion object{
        private const val TAG = "LoginViewModel"
    }

}