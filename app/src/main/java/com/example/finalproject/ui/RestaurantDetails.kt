package com.example.finalproject.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finalproject.R
import com.example.finalproject.databinding.ActivityRestaurantDetailsBinding

class RestaurantDetails: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityRestaurantDetailsBinding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(activityRestaurantDetailsBinding.root)


        // TODO initialize RestaurantDetailsAdapter
    }

    // TODO: Allow user to click address and get directions

    // TODO: Allow user to click phone number and call restaurant

}