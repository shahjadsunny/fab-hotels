package com.shahjad.fabhotels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shahjad.fabhotels.data.models.news.Article
import com.shahjad.fabhotels.util.Event

class MainActivityViewModel:ViewModel() {
     val article = MutableLiveData<Article>()
     val _updateArticle = MutableLiveData<Event<Article>>()
     val updateArticle : LiveData<Event<Article>> = _updateArticle
}