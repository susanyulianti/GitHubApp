package com.susan.githubapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingGitHubAppViewModel (private val pref: SettingGitHubAppPreferences) : ViewModel() {
    fun getSettingThemes(): LiveData<Boolean> {
        return pref.getGitHubAppTheme().asLiveData()
    }

    fun saveSettingThemes(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveGitHubAppTheme(isDarkModeActive)
        }
    }
}