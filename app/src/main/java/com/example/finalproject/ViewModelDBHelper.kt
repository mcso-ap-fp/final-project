package com.example.finalproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.UserPreferences
import com.google.firebase.firestore.FirebaseFirestore

class ViewModelDBHelper() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userPreferenceCollection = "user_preferences"

    private fun dbFetchUserPreferences(userPreferencesList: MutableLiveData<List<UserPreferences>>) {
        db.collection(userPreferenceCollection)
            .orderBy("timeStamp")//, Query.Direction.DESCENDING)
            .limit(10)
            .get()
            .addOnSuccessListener { result ->
               // TODO: Post to view model
            }
            .addOnFailureListener {
            }
    }

    fun createUserPreferences(
        userPreferences: UserPreferences,
        userPreferencesList: MutableLiveData<List<UserPreferences>>
    ) {
        db.collection(userPreferenceCollection)
            .add(userPreferences)
            .addOnSuccessListener {
                dbFetchUserPreferences(userPreferencesList)
            }
            .addOnFailureListener { e ->
                Log.e(javaClass.simpleName, "Error ", e)
            }
    }
}