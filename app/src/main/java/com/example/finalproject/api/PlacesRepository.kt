package com.example.finalproject.api

class PlacesRepository (private val api: PlacesApi) {

    suspend fun getRestaurants(city: String, apikey : String) : List<RestaurantData> {
        var resturants = api.getRestaurant(city, apikey)
        var outputRestaurants = mutableListOf<RestaurantData>()

        for (item in resturants.results) {
            outputRestaurants.add(item)
        }
        return outputRestaurants
    }

    suspend fun getDirections(origin: String, destination: String, apikey: String) : List<Route> {
        var response = api.getDirections(origin, destination, apikey)
        return response.routes
    }

}