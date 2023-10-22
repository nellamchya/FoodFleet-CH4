package com.shine.foodfleet.settings


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shine.foodfleet.data.local.datastore.UserPreferenceDataSource
import kotlinx.coroutines.launch

class SettingsViewModel(private val userPreferenceDataSource: UserPreferenceDataSource) : ViewModel() {

    fun setUserDarkModePref(isUsingDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferenceDataSource.setUserDarkModePref(isUsingDarkMode)
        }
    }

}