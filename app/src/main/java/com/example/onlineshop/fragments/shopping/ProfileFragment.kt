package com.example.onlineshop.fragments.shopping

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.onlineshop.BuildConfig
import com.example.onlineshop.R
import com.example.onlineshop.activities.LoginRegisterActivity
import com.example.onlineshop.databinding.FragmentProfileBinding
import com.example.onlineshop.util.Resource
import com.example.onlineshop.util.showBottomNavigationView
import com.example.onlineshop.viewmodel.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.constraintProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_userAccountFragment)
        }

        binding.linearAllOrders.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_ordersFragment)
        }

        binding.linearBilling.setOnClickListener {
            val action =
                ProfileFragmentDirections.actionProfileFragmentToBillingFragment(0f, emptyArray())
            findNavController().navigate(action)
        }

        binding.textViewVersion.text = "Version ${BuildConfig.VERSION_CODE}"

        onLogoutClick()

        lifecycleScope.launch {
            viewModel.user.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBarSettings.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBarSettings.visibility = View.GONE
                        Glide.with(requireView()).load(it.data!!.imagePath).error(
                            ColorDrawable(
                                Color.BLACK
                            )
                        ).into(binding.imageUser)
                        binding.textViewUserName.text = "${it.data.firstName} ${it.data.lastName}"
                    }

                    is Resource.Error -> {
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        binding.progressBarSettings.visibility = View.GONE
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        showBottomNavigationView()
    }

    private fun onLogoutClick() {
        binding.linearLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(context, LoginRegisterActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}