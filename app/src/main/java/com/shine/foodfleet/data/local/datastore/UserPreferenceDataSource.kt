package com.shine.foodfleet.data.local.datastore

import androidx.datastore.preferences.core.intPreferencesKey
import com.shine.foodfleet.utils.PreferenceDataStoreHelper
import kotlinx.coroutines.flow.Flow

interface UserPreferenceDataSource {
    fun getUserLayoutModePrefFlow(): Flow<Int>
    suspend fun setUserLayoutModePref(layoutMode: Int)
}

class UserPreferenceDataSourceImpl(private val preferenceHelper: PreferenceDataStoreHelper) :
    UserPreferenceDataSource {

    override fun getUserLayoutModePrefFlow(): Flow<Int> {
        return preferenceHelper.getPreference(
            PREF_USER_LAYOUT_MODE,
            1
        )
    }

    override suspend fun setUserLayoutModePref(layoutMode: Int) {
        return preferenceHelper.putPreference(PREF_USER_LAYOUT_MODE, layoutMode)
    }

    companion object {
        val PREF_USER_LAYOUT_MODE = intPreferencesKey("PREF_USER_LAYOUT_MODE")
    }
}
