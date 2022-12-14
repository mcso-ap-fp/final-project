package com.example.finalproject

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.finalproject.model.UserPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class ViewModelDBHelper() {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userPreferenceCollection = "user_preferences"

    fun dbFetchUserPreferences(userPreferencesList: MutableLiveData<List<UserPreferences>>) {
        db.collection(userPreferenceCollection)
            .whereEqualTo("user", FirebaseAuth.getInstance().currentUser?.uid)
            .get()
            .addOnSuccessListener { result ->
                userPreferencesList.postValue(result.documents.mapNotNull {
                    it.toObject(UserPreferences::class.java)
                })
            }
            .addOnFailureListener { e ->
                Log.e(javaClass.simpleName, "Error ", e)
            }
    }

    fun addOrUpdateUserPreferences(
        userPreferences: List<UserPreferences>,
        userPreferencesList: MutableLiveData<List<UserPreferences>>
    ) {
        db.runBatch {
            userPreferences.forEach {
                val id = it.firestoreID ?: UUID.randomUUID().toString()
                db.collection(userPreferenceCollection)
                    .document(id)
                    .set(it)
            }
        }.addOnSuccessListener {
            dbFetchUserPreferences(userPreferencesList)
        }.addOnFailureListener { e ->
            Log.e(javaClass.simpleName, "Error ", e)
        }
    }
}