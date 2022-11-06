package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.ui.HomeFragment
import com.example.finalproject.ui.RestaurantDetails
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract


class MainActivity : AppCompatActivity() {

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        AuthInit(signInLauncher)

        addHomeFragment()

        activityMainBinding.contentMain.viewResults.setOnClickListener {
            launchRestaurantDetailActivity()
        }
    }

    private fun addHomeFragment() {
        supportFragmentManager.commit {
            add(R.id.main_frame, HomeFragment.newInstance(), HomeFragment.tag)
        }
    }

    private fun launchRestaurantDetailActivity () {
        val restaurantDetailIntent = Intent(this, RestaurantDetails::class.java)
        startActivity(restaurantDetailIntent)
    }

}