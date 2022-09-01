package com.shahjad.fabhotels.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.shahjad.fabhotels.MainActivityViewModel
import com.shahjad.fabhotels.adaptors.NewsAdaptor
import com.shahjad.fabhotels.data.local.AppSharedPreference
import com.shahjad.fabhotels.data.models.news.Article
import com.shahjad.fabhotels.databinding.FragmentNewsBinding
import com.shahjad.fabhotels.util.Event
import com.shahjad.fabhotels.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() , ArticleCallback {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var appSharedPreference: AppSharedPreference
    private var _binding: FragmentNewsBinding? = null
    private var newsAdaptor: NewsAdaptor? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        _binding = FragmentNewsBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this
            it.viewmodel = viewModel
        }

        setAdaptor()
        updateArticleLike()
        showErrorMsg()
        openNewsDetail()
        mainActivityViewModel.updateArticle.observe(this,EventObserver{
            Log.i(TAG, "onCreateView: update view:$it")
            viewModel.likeUpdate(it,it.position)
        })
        return binding.root
    }

    private fun updateArticleLike() {
        viewModel.position.observe(viewLifecycleOwner,EventObserver{
            newsAdaptor?.notifyItemChanged(it)
        })
    }

    private fun openNewsDetail() {

        viewModel.openArticleEvent.observe(viewLifecycleOwner,EventObserver{
            mainActivityViewModel.article.postValue( it)
            findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToArticleDetailFragment())
        })
    }

    private fun setAdaptor() {

        val viewModel = binding.viewmodel
        if (viewModel != null) {
            newsAdaptor = NewsAdaptor(viewModel)
            binding.newsListView.adapter = newsAdaptor
//            _binding.newsAdaptor = newsAdaptor
        } else {
//            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun showErrorMsg() {

        viewModel.msg.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            Log.i(TAG, "showErrorMsg: $it")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "NewsFragment"
    }

    override fun updateLike(article: Article) {


    }


}

interface ArticleCallback{
    fun updateLike(article: Article)
}