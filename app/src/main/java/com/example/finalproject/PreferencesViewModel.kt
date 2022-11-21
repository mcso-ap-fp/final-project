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

//
//    fun updateNote(position: Int, text: String, pictureUUIDs: List<String>) {
//        val note = getNote(position)
//        // Have to update text before calling updateNote
//        note.text = text
//        note.pictureUUIDs = pictureUUIDs
//        dbHelp.updateNote(note, notesList)
//    }

}