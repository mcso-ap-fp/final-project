package com.example.finalproject.model

class CuisineRepository {
    // ToDo: Add more. Store these in DB?
    private var cuisines = listOf("No Preference", "American", "Mexican", "Italian", "Chinese", "Japanese", "Thai", "Indian")

    fun fetchData(): List<String> {
        return cuisines
    }
}