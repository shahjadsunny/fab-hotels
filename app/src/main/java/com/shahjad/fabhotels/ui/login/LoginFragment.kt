package com.shahjad.fabhotels.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.shahjad.fabhotels.databinding.FragmentLoginBinding
import com.shahjad.fabhotels.util.EventObserver
import com.shahjad.fabhotels.util.MyUtility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private lateinit var viewModel: LoginViewModel
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        _binding = FragmentLoginBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = this
            it.viewmodel = viewModel
        }


        onSuccess()
        showErrorMsg()
        return binding.root
    }

    private fun onSuccess() {
        viewModel.success.observe(viewLifecycleOwner, EventObserver {

//            Toast.makeText(requireContext(),it.toString(),Toast.LENGTH_SHORT).show()
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNewsFragment())
//            Log.i(TAG, "onSuccess: $it")
        }
        )

    }

    companion object{
        private const val TAG = "LoginFragment"
    }
    private fun showErrorMsg() {

        viewModel.msg.observe(viewLifecycleOwner) {
            MyUtility.showSnackbarMessage(binding.rootView,it)
//            Toast.makeText(requireContext(),it,Toast.LENGTH_SHORT).show()
//            Log.i(TAG, "showMsg: $it")
        }
    }


}