package com.shine.foodfleet.presentation.feature.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ProfileViewModel : ViewModel() {
    private val _profileText = MutableLiveData<String>()
    val profileText: LiveData<String> = _profileText

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _password = MutableLiveData<String>()
    val password: LiveData<String> = _password

    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    private val _telepon = MutableLiveData<String>()
    val telepon: LiveData<String> = _telepon

    fun updateProfileText(newText: String) {
        _profileText.value = newText
    }

    fun updateUsername(newUsername: String) {
        _username.value = newUsername
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updateTelepon(newTelepon: String) {
        _telepon.value = newTelepon
    }
}
