package com.example.finalproject.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.api.RestaurantData
import com.example.finalproject.databinding.RestaurantDetailsBinding

class RestaurantDetailsAdapter :
    ListAdapter<RestaurantData, RestaurantDetailsAdapter.VH>(DiffCallback()) {

    inner class VH(val restaurantDetailsBinding: RestaurantDetailsBinding) :
        RecyclerView.ViewHolder(restaurantDetailsBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantDetailsAdapter.VH {
        val binding = RestaurantDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val binding = holder.restaurantDetailsBinding

        // TODO bind restaurant details to layout
    }


    // https://medium.com/android-news/recylerview-list-adapter-template-in-kotlin-6b9814201458
    class DiffCallback : DiffUtil.ItemCallback<RestaurantData>() {
        override fun areItemsTheSame(
            oldItem: RestaurantData,
            newItem: RestaurantData
        ): Boolean {
            return oldItem.restaurantName == newItem.restaurantName
        }

        override fun areContentsTheSame(
            oldItem: RestaurantData,
            newItem: RestaurantData
        ): Boolean {
            return oldItem == newItem
        }
    }

}