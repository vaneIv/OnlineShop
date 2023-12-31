package com.example.onlineshop.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineshop.adapters.BillingProductsAdapter
import com.example.onlineshop.data.order.OrderStatus
import com.example.onlineshop.data.order.getOrderStatus
import com.example.onlineshop.databinding.FragmentOrderDetailsBinding
import com.example.onlineshop.util.VerticalItemDecoration

class OrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding

    private val billingProductsAdapter by lazy { BillingProductsAdapter() }

    private val args by navArgs<OrderDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailsBinding.inflate(inflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val order = args.order

        setupRecyclerView()

        binding.apply {
            textViewOrderId.text = "Order #${order.orderId}"

            stepView.setSteps(
                mutableListOf(
                    OrderStatus.Ordered.status,
                    OrderStatus.Confirmed.status,
                    OrderStatus.Shipped.status,
                    OrderStatus.Delivered.status,
                )
            )

            val currentOrderState = when (getOrderStatus(order.orderStatus)) {
                is OrderStatus.Ordered -> 0
                is OrderStatus.Confirmed -> 1
                is OrderStatus.Shipped -> 2
                is OrderStatus.Delivered -> 3
                else -> 0
            }

            stepView.go(currentOrderState, false)
            if (currentOrderState == 3) {
                stepView.done(true)
            }

            textViewFullName.text = order.address.fullName
            textViewAddress.text = "${order.address.street} ${order.address.city}"
            textViewPhoneNumber.text = order.address.phone

            textViewTotalPrice.text = "$ ${order.totalPrice}"
        }

        billingProductsAdapter.differ.submitList(order.products)
    }

    private fun setupRecyclerView() {
        binding.recyclerViewProducts.apply {
            adapter = billingProductsAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(VerticalItemDecoration())
        }
    }
}