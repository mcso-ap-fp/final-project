package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.databinding.ContentMainBinding
import com.example.finalproject.ui.HomeFragment
import com.example.finalproject.ui.PreferencesFragment
import com.example.finalproject.ui.RestaurantDetails
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ContentMainBinding

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        binding = activityMainBinding.contentMain

        AuthInit(signInLauncher)

        addHomeFragment()
        preferencesFragment()

        activityMainBinding.contentMain.viewResults.setOnClickListener {
            launchRestaurantDetailActivity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.logout -> {
            logout()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun addHomeFragment() {
        supportFragmentManager.commit {
            add(R.id.main_frame, HomeFragment.newInstance(), HomeFragment.tag)
        }
    }

    private fun preferencesFragment() {
       binding.updatePreferences.setOnClickListener {
           supportFragmentManager.commit {
               replace(R.id.main_frame, PreferencesFragment.newInstance(), PreferencesFragment.tag)
           }
       }
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()

        // Go back to login page
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build())

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()

        signInLauncher.launch(signInIntent)
    }

    private fun launchRestaurantDetailActivity () {
        val restaurantDetailIntent = Intent(this, RestaurantDetails::class.java)
        startActivity(restaurantDetailIntent)
    }

}