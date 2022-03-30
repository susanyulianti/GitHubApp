package com.susan.githubapp

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingGitHubAppPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getGitHubAppTheme(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveGitHubAppTheme(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingGitHubAppPreferences? = null

        fun getGitHubInstanceApp(dataStore: DataStore<Preferences>): SettingGitHubAppPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingGitHubAppPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}