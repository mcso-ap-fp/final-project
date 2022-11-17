package com.example.finalproject.model

class CuisineRepository {
    // ToDo: Add more. Store these in DB?
    private var cuisines = listOf<String>("American", "Mexican", "Italian")

    fun fetchData(): List<String> {
        return cuisines
    }
}