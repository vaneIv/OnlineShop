package com.example.onlineshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.bumptech.glide.Glide
import com.example.onlineshop.databinding.ViewpagerImageItemBinding

class ViewPagerImagesAdapter :
    Adapter<ViewPagerImagesAdapter.ViewPagerImagesViewHolder>() {

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    inner class ViewPagerImagesViewHolder(val binding: ViewpagerImageItemBinding) :
        ViewHolder(binding.root) {

        fun bind(imagePath: String) {
            Glide.with(itemView).load(imagePath).into(binding.imageViewProductDetails)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerImagesViewHolder {
        return ViewPagerImagesViewHolder(
            ViewpagerImageItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewPagerImagesViewHolder, position: Int) {
        val image = differ.currentList[position]
        holder.bind(image)
    }
}