package com.example.finalproject.ui

import android.R
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.PreferencesViewModel
import com.example.finalproject.api.RestaurantData
import com.example.finalproject.databinding.ActivityRestaurantDetailsBinding
import com.example.finalproject.model.PreferenceTypes
import com.google.android.material.slider.LabelFormatter

class RestaurantDetails: AppCompatActivity() {
    companion object {
        const val radiusKey = "radius"
        const val maxPriceKey = "maxPrice"
        const val cuisineKey = "cuisine"
    }

    private val viewModel: MainViewModel by viewModels()
    private val preferencesViewModel: PreferencesViewModel by viewModels()
    private var restaurantList: List<RestaurantData> = emptyList()
    private var radius: String? = null
    private var maxPrice: String? = null
    private var cuisine: String? = null

    private fun initAdapter(binding: ActivityRestaurantDetailsBinding, radius: String, price: String, cuisine: String?) : RestaurantDetailsAdapter {
        val rv = binding.recyclerView
        val manager = LinearLayoutManager(binding.recyclerView.context)
        rv.layoutManager = manager
        val adapter = RestaurantDetailsAdapter(radius, price, cuisine, ::launchDetailsActivity)
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

        radius = intent?.extras?.getString(radiusKey)
        maxPrice = intent?.extras?.getString(maxPriceKey)
        cuisine = intent?.extras?.getString(cuisineKey)

        // If coming back from another activity & in need of preferences
        if (radius == null) {
            preferencesViewModel.fetchInitialPreferences()
        }

        initRecyclerViewDividers(activityRestaurantDetailsBinding.recyclerView)
        var adapter = initAdapter(activityRestaurantDetailsBinding, radius!!, maxPrice!!, cuisine)
        activityRestaurantDetailsBinding.recyclerView.adapter = adapter

        viewModel.observeRestaurants().observe(this){
            restaurantList = it
            adapter.submitList(it) {
                activityRestaurantDetailsBinding.recyclerView.scrollToPosition(0)
            }
        }

        preferencesViewModel.observePreferences().observe(this) {
            it.forEach { preference ->
                when (preference.preferenceType) {
                    PreferenceTypes.CUISINE.type -> {
                        cuisine = preference.preferenceValue
                    }
                    PreferenceTypes.PRICE.type -> {
                        maxPrice = preference.preferenceValue
                    }
                    PreferenceTypes.DISTANCE.type -> {
                        radius = (preference.preferenceValue.toDouble() * 1609.34).toString()
                    }
                }
            }
        }

        initSortSpinner(activityRestaurantDetailsBinding)

        if(restaurantList.isEmpty()) {
            viewModel.netRestaurants(radius, maxPrice, cuisine)
        }
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

    fun getResults(r: String, d: String, c: String) {
        radius = r
        maxPrice = d
        cuisine = c
    }

    private fun launchDetailsActivity(intent: Intent) {
        resultLauncher.launch(intent)
    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (resultCode == RESULT_OK) {
//
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private var resultLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                maxPrice = result.data?.getStringExtra("price")
                radius = result.data?.getStringExtra("distance")
                cuisine = result.data?.getStringExtra("cuisine")
            }
        }


}