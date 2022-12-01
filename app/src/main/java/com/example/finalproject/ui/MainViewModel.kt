package com.example.finalproject.ui

import android.content.Context
import android.content.Intent
import android.webkit.WebStorage.Origin
import androidx.lifecycle.*
import com.example.finalproject.api.*
import com.google.android.datatransport.runtime.Destination
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class MainViewModel : ViewModel() {
    private val placesApi = PlacesApi.create()
    private val placesRepository = PlacesRepository(placesApi)
    private val currentRestaurants = MutableLiveData<List<RestaurantData>>()
    private val currentDirections = MutableLiveData<List<Route>>()
    private val currentSingleRestaurant = MutableLiveData<RestaurantDetailsData>()

    private val userDisplayName = MutableLiveData<String>()

    private val apiKey = "AIzaSyDpDP44Eof2LUs__NZ32Xm_uhwrsFICGZM"


    // Load new restaurants
    fun netRestaurants(radius: String?, maxPrice: String?, cuisine: String?){
        var cuisineSearch = "restaurants"
        cuisine?.let { cuisineSearch = "$it restaurants"}

        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO){
            val restaurants = placesRepository.getRestaurants(cuisineSearch, apiKey, radius, maxPrice)
            currentRestaurants.postValue(restaurants)
        }
    }

    fun observeRestaurants(): LiveData<List<RestaurantData>> {
        return currentRestaurants
    }

    // Load directions to restaurant
    fun netDirections(restaurant_address:String, origin: String){
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO){
            currentDirections.postValue(placesRepository.getDirections(origin, restaurant_address, apiKey))
        }
    }

    fun getDirections(): LiveData<List<Route>> {
        return currentDirections
    }

    // Load details of single restaurant
    fun netSingleRestaurant(place_id: String){
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO){
            currentSingleRestaurant.postValue(placesRepository.getRestaurantDetails(place_id, apiKey))
        }
    }

    fun observeSingleRestaurant(): LiveData<RestaurantDetailsData>{
        return currentSingleRestaurant
    }

    fun setUserDisplayName() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        userDisplayName.postValue(currentUser?.displayName)
    }

    fun observeUserDisplayName(): LiveData<String> {
        return userDisplayName
    }

}
