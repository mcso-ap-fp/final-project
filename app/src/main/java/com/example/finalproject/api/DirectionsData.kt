package com.example.finalproject.api

import com.google.android.gms.maps.model.Polyline
import com.google.gson.annotations.SerializedName
import java.util.Objects

data class DirectionsData (
    // TODO map response to data class
    @SerializedName("geocoded_wayponts")
    val waypoints: List<String>,
    @SerializedName("routes")
    val routes : List<Route>
)

data class Route (
    @SerializedName("bounds")
    val bounds: Objects,
    @SerializedName("copyrights")
    val copyrights: String,
    @SerializedName("legs")
    val legs: List<Leg>
)

data class Leg (
    @SerializedName("distance")
    val distance: Objects,
    @SerializedName("steps")
    val steps: List<Step>
    )

data class Step (
    @SerializedName("polyline")
    val polyline: Points
        )

data class Points(
    @SerializedName("points")
    val points : String
)