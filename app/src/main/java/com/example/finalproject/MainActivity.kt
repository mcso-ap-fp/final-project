package com.example.finalproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import androidx.activity.viewModels
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.databinding.ContentMainBinding
import com.example.finalproject.ui.HomeFragment
import com.example.finalproject.ui.PreferencesFragment
import com.example.finalproject.ui.MainViewModel
import com.example.finalproject.ui.RestaurantDetails
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.firebase.auth.FirebaseAuth
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ContentMainBinding

    private val signInLauncher =
        registerForActivityResult(FirebaseAuthUIActivityResultContract()) {
            viewModel.setUserDisplayName()
        }


    companion object {
        const val directionsKey = "directionsKey"
        const val placeIdKey = "placeIdKey"
        const val restaurantNameKey = "restaurantNameKey"
    }

    private lateinit var placesClient: PlacesClient
    private val viewModel: MainViewModel by viewModels()
    private val apiKey = "AIzaSyDpDP44Eof2LUs__NZ32Xm_uhwrsFICGZM"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        binding = activityMainBinding.contentMain

        AuthInit(signInLauncher)
        viewModel.setUserDisplayName()

        addHomeFragment()

//        activityMainBinding.contentMain.viewResults.setOnClickListener {
//            launchRestaurantDetailActivity()
//        }
        //Places.initialize(applicationContext, apiKey)
        //placesClient = Places.createClient(this)

        //var request = {

        //    types :
        //}
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

     fun loadPreferencesFragment() {
            supportFragmentManager.commit {
                replace(R.id.main_frame, PreferencesFragment.newInstance(), PreferencesFragment.tag).addToBackStack(PreferencesFragment.tag)
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

        // We want to direct the user back to the home fragment on login
        supportFragmentManager.commit {
            replace(R.id.main_frame, HomeFragment.newInstance(), HomeFragment.tag)
        }

        signInLauncher.launch(signInIntent)
    }

    fun launchRestaurantDetailActivity (radius: String, maxPrice: String, cuisine: String?) {
        val restaurantDetailIntent = Intent(this, RestaurantDetails::class.java)
        restaurantDetailIntent.putExtra(RestaurantDetails.radius, radius)
        restaurantDetailIntent.putExtra(RestaurantDetails.maxPrice, maxPrice)
        restaurantDetailIntent.putExtra(RestaurantDetails.cuisine, cuisine)
        startActivity(restaurantDetailIntent)
    }

}