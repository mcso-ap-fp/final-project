package com.example.finalproject.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.databinding.ActivityRestaurantDetailsBinding
import com.example.finalproject.databinding.RestaurantDetailsBinding

class RestaurantDetails: AppCompatActivity() {
    companion object {
        const val radius = "radius"
        const val maxPrice = "maxPrice"
    }

    private val viewModel: MainViewModel by viewModels()

    private fun initAdapter(binding: ActivityRestaurantDetailsBinding) : RestaurantDetailsAdapter {
        val rv = binding.recyclerView
        val manager = LinearLayoutManager(binding.recyclerView.context)
        rv.layoutManager = manager
        val adapter = RestaurantDetailsAdapter()
        rv.adapter = adapter
        return adapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityRestaurantDetailsBinding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(activityRestaurantDetailsBinding.root)

        var adapter = initAdapter(activityRestaurantDetailsBinding)
        activityRestaurantDetailsBinding.recyclerView.adapter = adapter

        viewModel.observeRestaurants().observe(this){
            adapter.submitList(it)
        }

        viewModel.netRestaurants(intent?.extras?.getString(radius), intent?.extras?.getString(
            maxPrice
        ))


        //activityRestaurantDetailsBinding.root.
     //   viewModel.netRestaurants()
    }

    // TODO: Allow user to click phone number and call restaurant

}