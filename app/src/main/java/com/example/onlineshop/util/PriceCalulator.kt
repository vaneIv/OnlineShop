package com.example.onlineshop.util

fun Float?.getProductPrice(price: Float): Float {
    // This --> Percentage
    if (this == null) {
        return price
    }

    val remainingPricePercentage = 1f - this
    val priceAfterOffer = remainingPricePercentage * price

    return priceAfterOffer
}