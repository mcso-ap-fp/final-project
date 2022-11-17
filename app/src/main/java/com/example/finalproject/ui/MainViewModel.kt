package com.example.finalproject.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.example.finalproject.api.PlacesApi
import com.example.finalproject.api.PlacesRepository
import com.example.finalproject.api.RestaurantData
import com.example.finalproject.api.Route
import com.google.android.datatransport.runtime.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import okhttp3.internal.wait

class MainViewModel : ViewModel() {
    private val placesApi = PlacesApi.create()
    private val placesRepository = PlacesRepository(placesApi)
    private val currentRestaurants = MutableLiveData<List<RestaurantData>>()
    private val currentDirections = MutableLiveData<List<Route>>()


    private val city = "restaurants austin"
    private val apiKey = "AIzaSyDpDP44Eof2LUs__NZ32Xm_uhwrsFICGZM"
    private val origin = "5350 Burnet Rd, Austin, TX 78756"
    private val destination = "5301 Aurora Dr, Austin, TX 78756"


    fun netRestaurants(){
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO){
            currentRestaurants.postValue(placesRepository.getRestaurants(city, apiKey))
        }
    }

    fun observeRestaurants(): LiveData<List<RestaurantData>> {
        return currentRestaurants
    }

    fun netDirections(){
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO){
            currentDirections.postValue(placesRepository.getDirections(origin, destination, apiKey))
        }
    }

    fun getDirections(): LiveData<List<Route>> {
        return currentDirections
    }
}