package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.ui.MainViewModel
import com.example.finalproject.ui.RestaurantDetails
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*


class MainActivity : AppCompatActivity() {

    companion object {
        const val directionsKey = "directionsKey"
    }

    private lateinit var placesClient: PlacesClient
    private val viewModel: MainViewModel by viewModels()
    private val apiKey = "AIzaSyDpDP44Eof2LUs__NZ32Xm_uhwrsFICGZM"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.contentMain.viewResults.setOnClickListener {
            launchRestaurantDetailActivity()
        }
        //Places.initialize(applicationContext, apiKey)
        //placesClient = Places.createClient(this)

        //var request = {

        //    types :
        //}
        viewModel.netRestaurants()


    }


    private fun launchRestaurantDetailActivity () {
        val restaurantDetailIntent = Intent(this, RestaurantDetails::class.java)
        startActivity(restaurantDetailIntent)

    }

}