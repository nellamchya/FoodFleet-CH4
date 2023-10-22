package com.shine.foodfleet.presentation.feature.splashscreen

import androidx.lifecycle.ViewModel
import com.shine.foodfleet.data.repository.UserRepository

class SplashViewModel(private val repository: UserRepository) : ViewModel() {

    fun isUserLoggedIn() = repository.isLoggedIn()

}