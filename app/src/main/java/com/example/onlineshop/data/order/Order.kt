package com.example.onlineshop.data.order

import com.example.onlineshop.data.Address
import com.example.onlineshop.data.CartProduct

data class Order(
    val orderStatus: String,
    val totalPrice: Float,
    val products: List<CartProduct>,
    val address: Address
)