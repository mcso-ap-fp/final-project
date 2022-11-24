package com.example.finalproject

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finalproject.model.UserPreferences

class PreferencesViewModel: ViewModel() {
    private val dbHelper = ViewModelDBHelper()

    private var preferences = MutableLiveData<List<UserPreferences>>()

    fun fetchInitialPreferences() {
        dbHelper.dbFetchUserPreferences(preferences)
    }

    fun observePreferences(): MutableLiveData<List<UserPreferences>> {
        return preferences
    }

    fun updatePreferences(newPreferences: List<UserPreferences>) {
        dbHelper.addOrUpdateUserPreferences(newPreferences, preferences)
    }

}