package com.shahjad.fabhotels.ui.dashboard

import android.util.Log
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.shahjad.fabhotels.R
import com.shahjad.fabhotels.adaptors.NewsAdaptor
import com.shahjad.fabhotels.data.models.news.Article
import com.shahjad.fabhotels.util.MyUtility
import kotlinx.coroutines.launch


@BindingAdapter("app:articles")
fun setItems(listView: RecyclerView, items: List<Article>?) {
    items?.let {
            (listView.adapter as NewsAdaptor).setData(items)
    }
}

//@BindingAdapter(value = ["app:articles","app:viewmodel"])
//@BindingAdapter("app:updateViews")
//fun updateViews(listView: RecyclerView, items: List<Article>?) {
//    items?.let {
//        listView.update()
//
//    }
//}

@BindingAdapter("android:load_image")
fun loadImage(view: ImageView?,imgUrl:String?) {
    view?.context?.let {
        imgUrl?.let {imgUrl->
            MyUtility.imageSet(imgUrl, view,R.drawable.button_background)
        }
    }

}
const val TAG = "BindingAdapter"
@BindingAdapter("android:set_like")
fun setLike(view: ImageView?,isLiked:Boolean) {
    Log.i(TAG, "setLike: $isLiked")
    view?.context?.let {
        if (isLiked){
            view.setImageResource(R.drawable.like)
        }else{
            view.setImageResource(R.drawable.un_like)

        }
    }

}