package com.shine.foodfleet.data.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.shine.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow


interface UserPreferenceDataSource {
    suspend fun getUserDarkModePref(): Boolean
    fun getUserDarkModePrefFlow(): Flow<Boolean>
    suspend fun setUserDarkModePref(isUsingDarkMode: Boolean)
    fun getUserLayoutModePrefFlow(): Flow<Int>
    suspend fun setUserLayoutModePref(layoutMode: Int)
}

class UserPreferenceDataSourceImpl(private val preferenceHelper: PreferenceDataStoreHelper) :
    UserPreferenceDataSource {

    override suspend fun getUserDarkModePref(): Boolean {
        return preferenceHelper.getFirstPreference(PREF_USER_DARK_MODE, false)
    }

    override fun getUserDarkModePrefFlow(): Flow<Boolean> {
        return preferenceHelper.getPreference(PREF_USER_DARK_MODE, false)
    }

    override suspend fun setUserDarkModePref(isUsingDarkMode: Boolean) {
        return preferenceHelper.putPreference(PREF_USER_DARK_MODE, isUsingDarkMode)
    }

    override fun getUserLayoutModePrefFlow(): Flow<Int> {
        return preferenceHelper.getPreference(PREF_USER_LAYOUT_MODE, 1)
    }

    override suspend fun setUserLayoutModePref(layoutMode: Int) {
        return preferenceHelper.putPreference(PREF_USER_LAYOUT_MODE, layoutMode)
    }

    companion object {
        val PREF_USER_DARK_MODE = booleanPreferencesKey("PREF_USER_DARK_MODE")
        val PREF_USER_LAYOUT_MODE = intPreferencesKey("PREF_USER_LAYOUT_MODE")
    }
}