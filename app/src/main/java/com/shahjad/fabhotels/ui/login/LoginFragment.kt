package com.shahjad.fabhotels.ui.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shahjad.fabhotels.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

//    companion object {
//        fun newInstance() = LoginFragment()
//    }
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

        showMsg()
        return binding.root
    }

    companion object{
        private const val TAG = "LoginFragment"
    }
    private fun showMsg() {

        viewModel.msg.observe(viewLifecycleOwner) {
            Log.i(TAG, "showMsg: $it")
        }
    }


}