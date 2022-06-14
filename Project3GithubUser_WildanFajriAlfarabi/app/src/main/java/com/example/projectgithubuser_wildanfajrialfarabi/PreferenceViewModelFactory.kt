package com.example.projectgithubuser_wildanfajrialfarabi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PreferenceViewModelFactory (private val pref: OptionPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PreferencesViewModel::class.java)) {
            return PreferencesViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}