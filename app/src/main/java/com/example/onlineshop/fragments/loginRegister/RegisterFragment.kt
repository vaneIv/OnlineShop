package com.example.onlineshop.fragments.loginRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.onlineshop.data.User
import com.example.onlineshop.databinding.FragmentRegisterBinding
import com.example.onlineshop.util.Resource
import com.example.onlineshop.util.Resource.*
import com.example.onlineshop.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

const val TAG = "RegisterFragment"

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            buttonRegister.setOnClickListener {
                val user = User(
                    editTextFirstName.text.toString().trim(),
                    editTextLastName.text.toString().trim(),
                    editTextEmailRegister.text.toString().trim()
                )
                val password = editTextPassword.text.toString()

                viewModel.createAccountWithEmailAndPassword(user, password)
            }
        }

        lifecycleScope.launch {
            viewModel.register.collect {
                when (it) {
                    is Loading -> {
                        binding.buttonRegister.startAnimation()
                    }

                    is Success -> {
                        Log.d("test", it.data.toString())
                        binding.buttonRegister.startAnimation()
                    }

                    is Error -> {
                        Log.e(TAG, it.message.toString())
                        binding.buttonRegister.startAnimation()
                    }

                    else -> Unit
                }
            }
        }
    }
}