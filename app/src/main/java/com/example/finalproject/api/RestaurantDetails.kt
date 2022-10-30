package com.example.finalproject.api

import com.google.gson.annotations.SerializedName

data class RestaurantDetails (
    // TODO map response to data class
    @SerializedName("name")
    val restaurantName: String
        )