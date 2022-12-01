package com.example.finalproject.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.finalproject.MainActivity
import com.example.finalproject.api.RestaurantData
import com.example.finalproject.api.Review
import com.example.finalproject.databinding.RestaurantReviewsBinding

class ReviewsAdapter :
    ListAdapter<Review, ReviewsAdapter.VH>(DiffCallback()) {

    inner class VH(val restaurantReviewsBinding: RestaurantReviewsBinding) :
        RecyclerView.ViewHolder(restaurantReviewsBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsAdapter.VH {
        val binding = RestaurantReviewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VH(binding)
    }


    override fun onBindViewHolder(holder: VH, position: Int) {
        val binding = holder.restaurantReviewsBinding

        val item = getItem(position)


        binding.author.text = item.author_name
        binding.review.text = item.text
        binding.time.text = "- " + item.relative_time_description
        binding.rating.text = item.rating.toString()

    }


    // https://medium.com/android-news/recylerview-list-adapter-template-in-kotlin-6b9814201458
    class DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(
            oldItem: Review,
            newItem: Review
        ): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(
            oldItem: Review,
            newItem: Review
        ): Boolean {
            return oldItem == newItem
        }
    }

}