package com.example.onlineshop.fragments.shopping

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.R
import com.example.onlineshop.adapters.AddressAdapter
import com.example.onlineshop.adapters.BillingProductsAdapter
import com.example.onlineshop.data.CartProduct
import com.example.onlineshop.databinding.FragmentBillingBinding
import com.example.onlineshop.util.HorizontalItemDecoration
import com.example.onlineshop.util.Resource
import com.example.onlineshop.viewmodel.BillingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BillingFragment : Fragment() {

    private val viewModel by viewModels<BillingViewModel>()

    private lateinit var binding: FragmentBillingBinding

    private val args by navArgs<BillingFragmentArgs>()

    private val addressAdapter by lazy { AddressAdapter() }
    private val billingAdapter by lazy { BillingProductsAdapter() }

    private var products = emptyList<CartProduct>()
    private var totalPrice = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        products = args.products.toList()
        totalPrice = args.totalPrice
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBillingBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAddressRv()
        setupBullingRv()

        binding.imageViewAddress.setOnClickListener {
            findNavController().navigate(R.id.action_billingFragment_to_addressFragment)
        }

        lifecycleScope.launch {
            viewModel.address.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBarAddress.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        addressAdapter.differ.submitList(it.data)
                        binding.progressBarAddress.visibility = View.GONE
                    }

                    is Resource.Error -> {
                        binding.progressBarAddress.visibility = View.GONE
                        Toast.makeText(requireContext(), "Error ${it.message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> Unit
                }
            }
        }

        billingAdapter.differ.submitList(products)

        binding.textViewTotalPrice.text = "$ $totalPrice"
    }

    private fun setupAddressRv() {
        binding.recyclerViewAddress.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = addressAdapter
            addItemDecoration(HorizontalItemDecoration())
        }
    }

    private fun setupBullingRv() {
        binding.recyclerViewProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = billingAdapter
            addItemDecoration(HorizontalItemDecoration())
        }
    }
}