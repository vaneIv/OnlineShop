package com.example.onlineshop.fragments.shopping

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.R
import com.example.onlineshop.adapters.CartProductsAdapter
import com.example.onlineshop.databinding.FragmentCartBinding
import com.example.onlineshop.firebase.FirebaseCommon
import com.example.onlineshop.util.Resource
import com.example.onlineshop.util.VerticalItemDecoration
import com.example.onlineshop.viewmodel.CartViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartFragment : Fragment(R.layout.fragment_cart) {

    private lateinit var binding: FragmentCartBinding

    private val viewModel by activityViewModels<CartViewModel>()

    private val cartAdapter by lazy { CartProductsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()



        lifecycleScope.launch {
            viewModel.cartProducts.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.progressBarCart.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        binding.progressBarCart.visibility = View.INVISIBLE
                        if (it.data!!.isEmpty()) {
                            showEmptyCart()
                            hideOtherViews()
                        } else {
                            hideEmptyCart()
                            showOtherViews()
                            cartAdapter.differ.submitList(it.data)
                        }
                    }

                    is Resource.Error -> {
                        binding.progressBarCart.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        lifecycleScope.launch {
            viewModel.productPrice.collectLatest { price ->
                price?.let {
                    binding.textViewTotalPrice.text = "$ $price"
                }
            }
        }

        lifecycleScope.launch {
            viewModel.deleteDialog.collectLatest {
                val alertDialog = AlertDialog.Builder(requireContext()).apply {
                    setTitle("Delete item from cart")
                    setMessage("Do you want to delete this item from your cart?")
                    setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }

                    setPositiveButton("Yes") { dialog, _ ->
                        viewModel.deleteCartProduct(it)
                        dialog.dismiss()
                    }
                }
                alertDialog.create()
                alertDialog.show()
            }
        }

        cartAdapter.onProductClick = {
            val extras = Bundle().apply { putParcelable("product", it.product) }
            findNavController().navigate(R.id.action_cartFragment_to_productDetailsFragment, extras)
        }

        cartAdapter.onPlusClick = {
            viewModel.changeQuantity(it, FirebaseCommon.QuantityChanging.INCREASE)
        }

        cartAdapter.onMinusClick = {
            viewModel.changeQuantity(it, FirebaseCommon.QuantityChanging.DECREASE)
        }
    }

    private fun hideOtherViews() {
        binding.apply {
            recyclerViewCart.visibility = View.GONE
            totalBoxContainer.visibility = View.GONE
            buttonCheckout.visibility = View.GONE
        }
    }

    private fun showOtherViews() {
        binding.apply {
            recyclerViewCart.visibility = View.VISIBLE
            totalBoxContainer.visibility = View.VISIBLE
            buttonCheckout.visibility = View.VISIBLE
        }
    }

    private fun hideEmptyCart() {
        binding.apply {
            layoutEmptyCart.visibility = View.GONE
        }
    }

    private fun showEmptyCart() {
        binding.apply {
            layoutEmptyCart.visibility = View.VISIBLE
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerViewCart.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = cartAdapter
            addItemDecoration(VerticalItemDecoration())
        }
    }
}