package com.example.finalproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.UserPreferences
import com.google.firebase.firestore.FirebaseFirestore

class ViewModelDBHelper() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userPreferenceCollection = "user_preferences"

    fun dbFetchUserPreferences(userPreferencesList: MutableLiveData<List<UserPreferences>>) {
        db.collection(userPreferenceCollection)
            .orderBy("timeStamp")//, Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                userPreferencesList.postValue(result.documents.mapNotNull {
                    it.toObject(UserPreferences::class.java)
                })
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