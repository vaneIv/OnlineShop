package com.example.onlineshop.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.onlineshop.data.Product
import com.example.onlineshop.databinding.ProductRvItemBinding
import com.example.onlineshop.util.getProductPrice

class BestProductsAdapter : RecyclerView.Adapter<BestProductsAdapter.BestProductsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductsViewHolder {
        return BestProductsViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BestProductsViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)

        holder.itemView.setOnClickListener {
            onClick?.invoke(product)
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class BestProductsViewHolder(private val binding: ProductRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                val priceAfterOffer = product.offerPercentage.getProductPrice(product.price)
                textViewNewPrice.text = "${String.format("%.2f", priceAfterOffer)}"
                textViewPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                if (product.offerPercentage == null)
                    textViewNewPrice.visibility = View.INVISIBLE

                Glide.with(itemView).load(product.images[0]).into(imageViewProduct)
                textViewPrice.text = "${product.price}"
                textViewName.text = product.name
            }
        }
    }

    var onClick: ((Product) -> Unit)? = null
}