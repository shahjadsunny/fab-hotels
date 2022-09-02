package com.shahjad.fabhotels.ui.articledetail

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shahjad.fabhotels.MainActivityViewModel
import com.shahjad.fabhotels.data.models.news.Article
import com.shahjad.fabhotels.util.Event

class ArticleDetailViewModel : ViewModel() {

    private val _article = MutableLiveData<Article>()
    val article : LiveData<Article> = _article
    companion object{
        private const val TAG = "ArticleDetailViewModel"
    }
    fun onLike(article:Article,mainActivityViewModel: MainActivityViewModel){
        article.like = !article.like
        _article.value = article
        mainActivityViewModel._updateArticle.value = Event(article)
    }

    fun showArticleDetails(it: Article?) {
        it?.let {
            _article.value = it
        }
    }

}