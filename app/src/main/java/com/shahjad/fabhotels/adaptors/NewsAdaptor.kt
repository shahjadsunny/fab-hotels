package com.shahjad.fabhotels.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shahjad.fabhotels.data.models.news.Article
import com.shahjad.fabhotels.databinding.NewsArticleViewBinding
import com.shahjad.fabhotels.ui.dashboard.NewsViewModel
import kotlinx.coroutines.launch

class NewsAdaptor(private val viewModel: NewsViewModel): RecyclerView.Adapter< NewsAdaptor.ViewHolder>() {
    private val article = ArrayList<Article>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = article[position]

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: NewsArticleViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: NewsViewModel, article: Article) {

            binding.viewmodel = viewModel
            binding.article = article
            binding.position = layoutPosition
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsArticleViewBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
     fun setData(newRating: List<Article>) {
        viewModel.viewModelScope.launch {
            val diffCallback = TaskDiffCallback(article, newRating)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            article.clear()
            article.addAll(newRating)
            diffResult.dispatchUpdatesTo(this@NewsAdaptor)
        }

    }

    override fun getItemCount(): Int {
        return  article.size
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class TaskDiffCallback(private val oldList: List<Article>, private val newList: List<Article>)  : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

//    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
//        return oldItem.title == newItem.title
//    }
//
//    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
//        return oldItem == newItem
//    }
}
