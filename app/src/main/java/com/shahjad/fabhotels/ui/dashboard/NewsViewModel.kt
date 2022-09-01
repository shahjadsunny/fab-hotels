package com.shahjad.fabhotels.ui.dashboard

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahjad.fabhotels.data.NewsRepository
import com.shahjad.fabhotels.data.Result
import com.shahjad.fabhotels.data.local.AppSharedPreference
import com.shahjad.fabhotels.data.models.login.LoginModel
import com.shahjad.fabhotels.data.models.news.Article
import com.shahjad.fabhotels.data.models.news.NewsModel
import com.shahjad.fabhotels.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository,private val appSharedPreference: AppSharedPreference) : ViewModel() {

    private val _success = MutableLiveData<Event<NewsModel?>>()
    val success : LiveData<Event<NewsModel?>> = _success

    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg
    private val _progressBar = MutableLiveData<Int>()
    val progressBar : LiveData<Int> = _progressBar
    private val _articles = MutableLiveData<List<Article>> ()
    val articles: LiveData<List<Article>> = _articles
    init {
        getNews()
    }
    private fun getNews() {
        _progressBar.value = View.VISIBLE
        viewModelScope.launch {
            val response = newsRepository.getNews()
            updateUi(response)
            _progressBar.value = View.GONE
        }
    }

    private fun updateUi(response: Result<NewsModel>) {

        when(response){
            is Result.Success->{
                if (response.data.articles!=null && response.data.articles.isNotEmpty())
                    _articles.value = response.data.articles
                else{
                    _msg.value = "news not found"
                }
            }
            is Result.Error->{
                _msg.value = response.exception.message.toString()
            }
        }
    }
    fun openArticleDetail(artice:String){

    }
}