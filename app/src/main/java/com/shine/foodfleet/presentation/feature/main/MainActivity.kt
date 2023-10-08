package com.shine.foodfleet.presentation.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.shine.foodfleet.R
import com.shine.foodfleet.data.local.datastore.UserPreferenceDataSourceImpl
import com.shine.foodfleet.data.local.datastore.appDataStore
import com.shine.foodfleet.databinding.ActivityMainBinding
import com.shine.utils.GenericViewModelFactory
import com.shine.utils.PreferenceDataStoreHelperImpl

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MainViewModel by viewModels {
        val dataStore = this.appDataStore
        val dataStoreHelper = PreferenceDataStoreHelperImpl(dataStore)
        val userPreferenceDataSource = UserPreferenceDataSourceImpl(dataStoreHelper)
        GenericViewModelFactory.create(MainViewModel(userPreferenceDataSource))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupBottomNav()
        observeDarkModePref()
    }

    private fun setupBottomNav() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
    }

    private fun observeDarkModePref() {
        viewModel.userDarkModeLiveData.observe(this) { isUsingDarkMode ->
            AppCompatDelegate.setDefaultNightMode(if (isUsingDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}