package com.example.finalproject.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.databinding.ActivityRestaurantDetailsBinding

class RestaurantDetails: AppCompatActivity() {
    companion object {
        const val radius = "radius"
        const val maxPrice = "maxPrice"
        const val cuisine = "cuisine"
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

    private fun initRecyclerViewDividers(rv: RecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(
            rv.context, LinearLayoutManager.VERTICAL )
        rv.addItemDecoration(dividerItemDecoration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityRestaurantDetailsBinding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(activityRestaurantDetailsBinding.root)

        initRecyclerViewDividers(activityRestaurantDetailsBinding.recyclerView)
        var adapter = initAdapter(activityRestaurantDetailsBinding)
        activityRestaurantDetailsBinding.recyclerView.adapter = adapter

        viewModel.observeRestaurants().observe(this){
            adapter.submitList(it)
        }

        val radius: String? = intent?.extras?.getString(radius)
        val maxPrice: String? = intent?.extras?.getString(maxPrice)
        val cuisine: String? = intent?.extras?.getString(cuisine)
        viewModel.netRestaurants(radius, maxPrice, cuisine)
    }


}