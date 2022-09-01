package com.shahjad.fabhotels.ui.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.shahjad.fabhotels.adaptors.NewsAdaptor
import com.shahjad.fabhotels.data.local.AppSharedPreference
import com.shahjad.fabhotels.databinding.FragmentNewsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var appSharedPreference: AppSharedPreference
    private var _binding: FragmentNewsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        _binding = FragmentNewsBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this
            it.viewmodel = viewModel
        }

        setAdaptor()
        showErrorMsg()
        return binding.root
    }

    private fun setAdaptor() {

        val viewModel = binding.viewmodel
        if (viewModel != null) {
           val newsAdaptor = NewsAdaptor(viewModel)
            binding.newsListView.adapter = newsAdaptor
        } else {
//            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

    private fun onSuccess() {
//        viewModel.success.observe(viewLifecycleOwner, EventObserver {
//
//            Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
//             findNavController().navigate(NewsFragmentDirections.actionNewsFragmentToLoginFragment())
//            Log.i(onSuccess.TAG, "onSuccess: $it")
//        }
//        )

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

}