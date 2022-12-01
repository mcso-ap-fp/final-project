package com.example.finalproject.ui

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
            adapter.submitList(it) {
                activityRestaurantDetailsBinding.recyclerView.scrollToPosition(0)
            }
        }

        initSortSpinner(activityRestaurantDetailsBinding)

        val radius: String? = intent?.extras?.getString(radius)
        val maxPrice: String? = intent?.extras?.getString(maxPrice)
        val cuisine: String? = intent?.extras?.getString(cuisine)
        viewModel.netRestaurants(radius, maxPrice, cuisine)
    }

    private fun initSortSpinner(binding: ActivityRestaurantDetailsBinding) {
        val adapter = ArrayAdapter(
            binding.sort.context,
            R.layout.simple_spinner_item,
            listOf("None", "Rating", "Name")
        )

        binding.sort.adapter = adapter

        binding.sort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.setSortField(parent?.getItemAtPosition(position).toString())
            }
        }
    }

}