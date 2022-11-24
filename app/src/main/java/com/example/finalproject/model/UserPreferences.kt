package com.example.finalproject.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

enum class PreferenceTypes(val type: String) {
    CUISINE("cuisine"),
    DISTANCE("distance"),
    PRICE("price")
}

data class UserPreferences (
    @DocumentId var firestoreID: String = "",
    @ServerTimestamp val timeStamp: Timestamp? = null,
    var user: String = "",
    var preferenceType: String = "",
    var preferenceValue: String = ""
)