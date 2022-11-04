package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.ui.HomeFragment
import com.example.finalproject.ui.RestaurantDetails


class MainActivity : AppCompatActivity() {
    companion object {
        private const val homeFragTag = "homeFragTag"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        addHomeFragment()

        activityMainBinding.contentMain.viewResults.setOnClickListener {
            launchRestaurantDetailActivity()
        }
    }

    private fun addHomeFragment() {
        supportFragmentManager.commit {
            add(R.id.main_frame, HomeFragment.newInstance(), homeFragTag)
        }
    }

    private fun launchRestaurantDetailActivity () {
        val restaurantDetailIntent = Intent(this, RestaurantDetails::class.java)
        startActivity(restaurantDetailIntent)
    }

}