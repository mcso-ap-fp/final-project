package com.example.finalproject.model

import com.google.firebase.firestore.DocumentId

data class UserPreferences (
    @DocumentId var firestoreId: String,
    var user: String,
    var preferenceType: String,
    var preferenceValue: String
)