package com.shahjad.fabhotels.ui.dashboard

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.shahjad.fabhotels.MainActivityViewModel
import com.shahjad.fabhotels.R
import com.shahjad.fabhotels.adaptors.NewsAdaptor
import com.shahjad.fabhotels.data.local.AppSharedPreference
import com.shahjad.fabhotels.data.models.news.Article
import com.shahjad.fabhotels.databinding.FragmentNewsBinding
import com.shahjad.fabhotels.util.EventObserver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var mainActivitySharedViewModel: MainActivityViewModel
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
        mainActivitySharedViewModel =
            ViewModelProvider(requireActivity()).get(MainActivityViewModel::class.java)

        _binding = FragmentNewsBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this
            it.viewmodel = viewModel
        }

        setToolbar()

        setAdaptor()
        updateArticleLike()
        showErrorMsg()
        openNewsDetail()
        updateArticleFromDetailFrag()
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
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)
        toolbar.title = getString(R.string.dashboard)
        toolbar.setTitleTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.white
            )
        )
        setHasOptionsMenu(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId ){
                R.id.logout->{
                    appSharedPreference.setUserToken("")
                    appSharedPreference.setUserName("")
                    findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToLoginFragment())
                }
        }
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
    private fun updateArticleFromDetailFrag() {

        mainActivitySharedViewModel.updateArticle.observe(viewLifecycleOwner, EventObserver {
            Log.i(TAG, "onCreateView: update view:$it")
            viewModel.likeUpdate(it, it.position)
        })
    }

    private fun updateArticleLike() {
        viewModel.position.observe(viewLifecycleOwner, EventObserver {
            newsAdaptor?.notifyItemChanged(it)
        })
    }

    private fun openNewsDetail() {

        viewModel.openArticleEvent.observe(viewLifecycleOwner, EventObserver {
            mainActivitySharedViewModel.article.postValue(it)
            findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToArticleDetailFragment())
        })
    }

    private fun setAdaptor() {

        val viewModel = binding.viewmodel
        if (viewModel != null) {
            newsAdaptor = NewsAdaptor(viewModel)
            binding.newsListView.adapter = newsAdaptor
        } else {
            Log.i(TAG,"ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun showErrorMsg() {

        viewModel.msg.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "NewsFragment"
    }

}
