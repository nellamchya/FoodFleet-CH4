package com.shine.foodfleet.data.local.datastore

import app.cash.turbine.test
import com.shine.foodfleet.utils.PreferenceDataStoreHelper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UserPreferenceDataSourceImplTest {

    @MockK
    lateinit var preferenceDataStoreHelper: PreferenceDataStoreHelper

    private lateinit var userPreferenceDataSource: UserPreferenceDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        userPreferenceDataSource = UserPreferenceDataSourceImpl(preferenceDataStoreHelper)
    }

    @Test
    fun getUserLayoutModePrefFlow() {
        runTest {
            coEvery { preferenceDataStoreHelper.getPreference(any(), 1) } returns flow { emit(2) }
            userPreferenceDataSource.getUserLayoutModePrefFlow().test {
                val itemResult = awaitItem()
                TestCase.assertEquals(2, itemResult)
                awaitComplete()
            }
        }
    }

    @Test
    fun setUserLayoutModePref() {
        runTest {
            coEvery { preferenceDataStoreHelper.putPreference(any(), eq(3)) } returns Unit
            val result = userPreferenceDataSource.setUserLayoutModePref(3)
            coVerify { preferenceDataStoreHelper.putPreference(any(), eq(3)) }
            TestCase.assertEquals(result, Unit)
        }
    }
}
