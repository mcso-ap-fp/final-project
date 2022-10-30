package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.ui.RestaurantDetails


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.contentMain.viewResults.setOnClickListener {
            launchRestaurantDetailActivity()
        }
    }


    private fun launchRestaurantDetailActivity () {
        val restaurantDetailIntent = Intent(this, RestaurantDetails::class.java)
        startActivity(restaurantDetailIntent)
    }

}