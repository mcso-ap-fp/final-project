package com.example.finalproject.ui

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private fun initRecyclerViewDividers(rv: RecyclerView) {
        val dividerItemDecoration = DividerItemDecoration(
            rv.context, LinearLayoutManager.VERTICAL )
        rv.addItemDecoration(dividerItemDecoration)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val singleRestaurantDetailsBinding = ActivitySingleRestaurantDetailsBinding.inflate(layoutInflater)
        setContentView(singleRestaurantDetailsBinding.root)


        placeId = intent.getStringExtra(MainActivity.placeIdKey)!!
        restaurantName = intent.getStringExtra(MainActivity.restaurantNameKey)!!


        var adapter = initAdapter(singleRestaurantDetailsBinding)
        initRecyclerViewDividers(singleRestaurantDetailsBinding.reviewRV)
        singleRestaurantDetailsBinding.reviewRV.adapter = adapter

        singleRestaurantDetailsBinding.title.text = restaurantName

        viewModel.observeSingleRestaurant().observe(this){
            singleRestaurantDetailsBinding.phoneNumber.text = it.international_phone_number

            // Allow user to call restaurant
            singleRestaurantDetailsBinding.phoneNumber.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse("tel:" + singleRestaurantDetailsBinding.phoneNumber.text.toString())
                startActivity(intent)
            }

            singleRestaurantDetailsBinding.summary.text = it.editorial_summary?.overview

            singleRestaurantDetailsBinding.mon.text = it.current_opening_hours.weekday_text[0]
            singleRestaurantDetailsBinding.tues.text = it.current_opening_hours.weekday_text[1]
            singleRestaurantDetailsBinding.wed.text = it.current_opening_hours.weekday_text[2]
            singleRestaurantDetailsBinding.thur.text = it.current_opening_hours.weekday_text[3]
            singleRestaurantDetailsBinding.fri.text = it.current_opening_hours.weekday_text[4]
            singleRestaurantDetailsBinding.sat.text = it.current_opening_hours.weekday_text[5]
            singleRestaurantDetailsBinding.sun.text = it.current_opening_hours.weekday_text[6]

            if (it.curbside_pickup == true) {
                singleRestaurantDetailsBinding.curb.text = "Yes  "
                singleRestaurantDetailsBinding.curb.setTextColor(Color.GREEN)
            } else {
                singleRestaurantDetailsBinding.curb.text = "No  "
                singleRestaurantDetailsBinding.curb.setTextColor(Color.RED)
            }

            if (it.dine_in == true) {
                singleRestaurantDetailsBinding.din.text = "Yes  "
                singleRestaurantDetailsBinding.din.setTextColor(Color.GREEN)
            } else {
                singleRestaurantDetailsBinding.din.text = "No  "
                singleRestaurantDetailsBinding.din.setTextColor(Color.RED)
            }

            if (it.delivery == true) {
                singleRestaurantDetailsBinding.del.text = "Yes  "
                singleRestaurantDetailsBinding.del.setTextColor(Color.GREEN)
            } else {
                singleRestaurantDetailsBinding.del.text = "No  "
                singleRestaurantDetailsBinding.del.setTextColor(Color.RED)
            }

            if (it.current_opening_hours.open_now == true) {
                singleRestaurantDetailsBinding.open.text = "Yes  "
                singleRestaurantDetailsBinding.open.setTextColor(Color.GREEN)
            } else {
                singleRestaurantDetailsBinding.open.text = "No  "
                singleRestaurantDetailsBinding.open.setTextColor(Color.RED)
            }

            adapter.submitList(it.reviews)
        }

        viewModel.netSingleRestaurant(placeId)
    }

}