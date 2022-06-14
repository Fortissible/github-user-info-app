package com.example.projectgithubuser_wildanfajrialfarabi

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OptionPreferences(private val dataStore: DataStore<Preferences>) {

    private val themeKey = booleanPreferencesKey("theme_setting")

    companion object {
        @Volatile
        private var INSTANCE: OptionPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): OptionPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = OptionPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[themeKey] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkModeActive
        }
    }
}