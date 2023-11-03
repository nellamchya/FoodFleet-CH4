package com.shine.foodfleet.presentation.feature.profile

import androidx.lifecycle.ViewModel
import com.shine.foodfleet.data.repository.UserRepository

class ProfileViewModel(private val userRepo: UserRepository) : ViewModel() {

    fun getCurrentUser() = userRepo.getCurrentUser()

    fun doLogout() {
        userRepo.doLogout()
    }
}
