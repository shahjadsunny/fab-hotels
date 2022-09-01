package com.shahjad.fabhotels.ui.articledetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.shahjad.fabhotels.MainActivityViewModel
import com.shahjad.fabhotels.R
import com.shahjad.fabhotels.databinding.FragmentArticleDetailBinding
import com.shahjad.fabhotels.databinding.FragmentNewsBinding

class ArticleDetailFragment : Fragment() {

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var viewModel: ArticleDetailViewModel
    private var _binding: FragmentArticleDetailBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ArticleDetailViewModel::class.java)
        mainActivityViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this
            it.viewmodel = viewModel
            it.mainActivityViewModel = mainActivityViewModel
        }
//
        getDetailArticle()
        return binding.root
    }


    private fun getDetailArticle() {

        mainActivityViewModel.article.observe(viewLifecycleOwner) {
            Log.i(TAG, "getDetailArticle:$it ")
//            binding.article = it
            viewModel.showArticle(it)
//            viewModel.onLike(it,mainActivityViewModel )

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object{
        private const val TAG = "ArticleDetailFragment"
    }

}