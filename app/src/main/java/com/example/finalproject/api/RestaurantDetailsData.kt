package com.example.finalproject.api

import com.google.gson.annotations.SerializedName

data class RestaurantDetailsData (
    @SerializedName("curbside_pickup")
    val curbside_pickup: Boolean,
    @SerializedName("current_opening_hours")
    val current_opening_hours : OpeningHours,
    @SerializedName("delivery")
    val delivery : Boolean,
    @SerializedName("dine_in")
    val dine_in : Boolean,
    @SerializedName("user_rating_totals")
    val totalReviews : String,
    @SerializedName("editorial_summary")
    val editorial_summary : Summary,
    @SerializedName("international_phone_number")
    val international_phone_number : String,
    @SerializedName("reviews")
    val reviews : List<Review>
)

data class Review (
    @SerializedName("author_name")
    val author_name : String,
    @SerializedName("rating")
    val rating : Int,
    @SerializedName("relative_time_description")
    val relative_time_description : String,
    @SerializedName("text")
    val text : String
    )

data class Summary (
    @SerializedName("language")
    val language: String,
    @SerializedName("overview")
    val overview: String
        )

data class OpeningHours (
    @SerializedName("open_now")
    val open_now: Boolean,
    @SerializedName("weekday_text")
    val weekday_text : List<String>
        )