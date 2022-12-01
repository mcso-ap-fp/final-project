package com.example.finalproject.ui

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.MainActivity
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
        val item = getItem(position)
        //holder.itemView.

        binding.restaurantName.text = item.restaurantName
        binding.restaurantAddress.text = item.address
        binding.restaurantRating.text = item.rating
        //binding.menu.setBackgroundColor(Color.TRANSPARENT)

        binding.directions.setOnClickListener {
            val directions_intent = Intent(it.context, Directions::class.java)
            directions_intent.putExtra(MainActivity.directionsKey, binding.restaurantAddress.text.toString())
            it.context.startActivity(directions_intent)
        }

        binding.menu.setOnClickListener {
            val details_intent = Intent(it.context, SingleRestaurantDetails::class.java)
            details_intent.putExtra(MainActivity.placeIdKey, item.placeID)
            details_intent.putExtra(MainActivity.restaurantNameKey, binding.restaurantName.text)
            it.context.startActivity(details_intent)
        }

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