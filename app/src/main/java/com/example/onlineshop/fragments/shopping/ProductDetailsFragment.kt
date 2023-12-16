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
import com.example.onlineshop.R
import com.example.onlineshop.activities.ShoppingActivity
import com.example.onlineshop.adapters.ColorsAdapter
import com.example.onlineshop.adapters.SizesAdapter
import com.example.onlineshop.adapters.ViewPagerImagesAdapter
import com.example.onlineshop.data.CartProduct
import com.example.onlineshop.databinding.FragmentProductDetailsBinding
import com.example.onlineshop.util.Resource
import com.example.onlineshop.util.hideBottomNavigationView
import com.example.onlineshop.viewmodel.DetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {

    private lateinit var binding: FragmentProductDetailsBinding

    private val viewModel by viewModels<DetailsViewModel>()

    private val args by navArgs<ProductDetailsFragmentArgs>()

    private val viewPagerAdapter by lazy { ViewPagerImagesAdapter() }
    private val sizesAdapter by lazy { SizesAdapter() }
    private val colorsAdapter by lazy { ColorsAdapter() }

    private var selectedColor: Int? = null
    private var selectedSize: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideBottomNavigationView()
        binding = FragmentProductDetailsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = args.product

        setupSizesRecyclerView()
        setupColorsRecyclerView()
        setupViewPager()

        binding.imageViewClose.setOnClickListener {
            findNavController().navigateUp()
        }

        sizesAdapter.onItemClick = {
            selectedSize = it
        }

        colorsAdapter.onItemClick = {
            selectedColor = it
        }

        binding.buttonAddCart.setOnClickListener {
            viewModel.addUpdateProductInCart(CartProduct(product, 1, selectedColor, selectedSize))
        }

        lifecycleScope.launch {
            viewModel.addToChart.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        binding.buttonAddCart.startAnimation()
                    }

                    is Resource.Success -> {
                        binding.buttonAddCart.revertAnimation()
                        binding.buttonAddCart.setBackgroundColor(resources.getColor(R.color.black))
                    }

                    is Resource.Error -> {
                        binding.buttonAddCart.stopAnimation()
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }

                    else -> Unit
                }
            }
        }

        binding.apply {
            textViewProductName.text = product.name
            textViewProductPrice.text = "$ ${product.price}"
            textViewProductDescription.text = product.description

            if (product.colors.isNullOrEmpty()) {
                textViewProductColors.visibility = View.INVISIBLE
            }

            if (product.sizes.isNullOrEmpty()) {
                textViewProductSize.visibility = View.INVISIBLE
            }
        }

        viewPagerAdapter.differ.submitList(product.images)
        product.colors?.let { colorsAdapter.differ.submitList(it) }
        product.sizes?.let { sizesAdapter.differ.submitList(it) }
    }

    private fun setupViewPager() {
        binding.apply {
            viewPagerProductImages.adapter = viewPagerAdapter
        }
    }

    private fun setupColorsRecyclerView() {
        binding.recyclerViewColors.apply {
            adapter = colorsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupSizesRecyclerView() {
        binding.recyclerViewSizes.apply {
            adapter = sizesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }
}