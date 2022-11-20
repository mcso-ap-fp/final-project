package com.example.finalproject.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finalproject.MainActivity
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.databinding.ActivityRestaurantDetailsBinding
import com.example.finalproject.databinding.ActivitySingleRestaurantDetailsBinding
import com.example.finalproject.databinding.RestaurantDetailsBinding

class SingleRestaurantDetails: AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private var placeId: String = ""
    private var restaurantName: String = ""



    private fun initAdapter(binding: ActivitySingleRestaurantDetailsBinding) : ReviewsAdapter {
        val rv = binding.reviewRV
        val manager = LinearLayoutManager(binding.reviewRV.context)
        rv.layoutManager = manager
        val adapter = ReviewsAdapter()
        rv.adapter = adapter
        return adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val singleRestaurantDetailsBinding = ActivitySingleRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(singleRestaurantDetailsBinding.root)


        placeId = intent.getStringExtra(MainActivity.placeIdKey)!!
        restaurantName = intent.getStringExtra(MainActivity.restaurantNameKey)!!


        var adapter = initAdapter(singleRestaurantDetailsBinding)
        singleRestaurantDetailsBinding.reviewRV.adapter = adapter

        singleRestaurantDetailsBinding.title.text = restaurantName

        viewModel.observeSingleRestaurant().observe(this){
            singleRestaurantDetailsBinding.phoneNumber.text = it.international_phone_number

            singleRestaurantDetailsBinding.phoneNumber.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + singleRestaurantDetailsBinding.phoneNumber.text.toString())
                startActivity(intent)
            }

            singleRestaurantDetailsBinding.summary.text = it.editorial_summary.overview

            var hours_string = ""
            for (x in it.current_opening_hours.weekday_text) {
                hours_string += " "
                hours_string += x
            }

            singleRestaurantDetailsBinding.hours.text = hours_string

            adapter.submitList(it.reviews)
        }

        viewModel.netSingleRestaurant(placeId)
    }

    // TODO: Allow user to click address and get directions

    // TODO: Allow user to click phone number and call restaurant

}