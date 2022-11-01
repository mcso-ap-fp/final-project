package com.example.finalproject.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.example.finalproject.api.PlacesApi
import com.example.finalproject.api.PlacesRepository
import com.example.finalproject.api.RestaurantData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val placesApi = PlacesApi.create()
    private val placesRepository = PlacesRepository(placesApi)
    private val currentRestaurants = MutableLiveData<List<RestaurantData>>()

    private val apiKey = "AIzaSyDpDP44Eof2LUs__NZ32Xm_uhwrsFICGZM"
    private val city = "Austin"

    fun netRestaurants(){
        viewModelScope.launch(context = viewModelScope.coroutineContext + Dispatchers.IO){
            currentRestaurants.postValue(placesRepository.getRestaurants(city, apiKey))
        }
    }

}