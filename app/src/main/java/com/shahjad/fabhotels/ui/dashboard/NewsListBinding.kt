package com.shahjad.fabhotels.ui.dashboard

import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahjad.fabhotels.R
import com.shahjad.fabhotels.adaptors.NewsAdaptor
import com.shahjad.fabhotels.data.models.news.Article
import com.shahjad.fabhotels.util.MyUtility


@BindingAdapter("app:articles")
fun setItems(listView: RecyclerView, items: List<Article>?) {
    items?.let {
        (listView.adapter as NewsAdaptor).submitList(items)
    }
}

@BindingAdapter("android:load_image")
fun loadImage(view: ImageView?,imgUrl:String?) {
    view?.context?.let {
        imgUrl?.let {imgUrl->
            MyUtility.imageSet(imgUrl, view,R.drawable.button_background)
        }
    }

}