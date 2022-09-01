package com.shahjad.fabhotels.ui.dashboard

import android.view.View
import android.widget.ImageView
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

    //error msg
    private val _msg = MutableLiveData<String>()
    val msg : LiveData<String> = _msg

    //progress bar
    private val _progressBar = MutableLiveData<Int>()
    val progressBar : LiveData<Int> = _progressBar

    // news list
    private val _articles = MutableLiveData<List<Article>> ()
    val articles: LiveData<List<Article>> = _articles
    // news list
    private val _position = MutableLiveData<Event<Int>> ()
    val position: LiveData<Event<Int>> = _position

    //open news details
    private val  _openArticleEvent = MutableLiveData<Event<Article>>()
    val openArticleEvent: LiveData<Event<Article>> = _openArticleEvent

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
    fun openArticleDetail(article:Article,position: Int){
        article.position = position
        _openArticleEvent.value = Event(article)
    }
    fun onLike(position:Int){
        _articles.value?.get(position)?.like   = !_articles.value?.get(position)?.like!!
        _position.value = Event(position)
    }
    fun likeUpdate( article:Article,position:Int){
        _articles.value?.get(position)?.like  = article.like
        _position.value = Event(position)
    }
}