package com.shine.foodfleet.presentation.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shine.foodfleet.data.repository.UserRepository
import com.shine.foodfleet.model.User

class ProfileViewModel(private val userRepo: UserRepository) : ViewModel() {

    private val _getProfileResult = MutableLiveData<User?>()
    val getProfileResult: LiveData<User?>
        get() = _getProfileResult

    fun getProfileData() {
        val data = userRepo.getCurrentUser()
        _getProfileResult.postValue(data)
    }

    fun getCurrentUser() = userRepo.getCurrentUser()

    fun doLogout() {
        userRepo.doLogout()
    }
}
