package com.example.finalproject.api

class PlacesRepository (private val api: PlacesApi) {

    suspend fun getRestaurants(city: String, apikey: String, radius: String?, maxPrice: String?) : List<RestaurantData> {
        var resturants = api.getRestaurant(city, apikey, radius, maxPrice)
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

    suspend fun getRestaurantDetails(place_id: String, apikey: String) : RestaurantDetailsData {
        var details = api.getRestaurantDetails(place_id, apikey)
        return details.result
    }



}