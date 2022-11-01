package com.example.finalproject.api

import com.google.gson.annotations.SerializedName

data class RestaurantData (
    // TODO map response to data class
    @SerializedName("name")
    val restaurantName: String,
    @SerializedName("formatted_address")
    val address : String,
    @SerializedName("price_level")
    val price : Int,
    @SerializedName("rating")
    val rating : String,
    @SerializedName("user_rating_totals")
    val totalReviews : String
    )