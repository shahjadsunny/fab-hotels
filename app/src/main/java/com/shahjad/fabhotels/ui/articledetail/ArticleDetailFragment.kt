package com.shahjad.fabhotels.ui.articledetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.shahjad.fabhotels.MainActivityViewModel
import com.shahjad.fabhotels.R
import com.shahjad.fabhotels.databinding.FragmentArticleDetailBinding

class ArticleDetailFragment : Fragment() {

    private lateinit var mainActivitySharedViewModel: MainActivityViewModel
    private lateinit var viewModel: ArticleDetailViewModel
    private var _binding: FragmentArticleDetailBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ArticleDetailViewModel::class.java)
        mainActivitySharedViewModel = ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)
        _binding = FragmentArticleDetailBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this
            it.viewmodel = viewModel
            it.mainActivityViewModel = mainActivitySharedViewModel
        }
        setToolbar()
        getDetailArticle()
        return binding.root
    }

    private fun setToolbar() {

        binding.toolbar.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.purple_500
            )
        )
        val toolbar =  binding.toolbar.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.title = getString(R.string.article_detail)
        toolbar.setTitleTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }
    private fun getDetailArticle() {

        mainActivitySharedViewModel.article.observe(viewLifecycleOwner) {
            Log.i(TAG, "getDetailArticle:$it ")
            viewModel.showArticleDetails(it)
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