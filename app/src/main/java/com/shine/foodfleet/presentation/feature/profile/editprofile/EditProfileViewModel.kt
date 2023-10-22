package com.shine.foodfleet.presentation.feature.profile.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shine.foodfleet.data.repository.UserRepository
import com.shine.utils.ResultWrapper
import kotlinx.coroutines.launch

class EditProfileViewModel(private val userRepo : UserRepository): ViewModel() {

    private val _changeProfileResult = MutableLiveData<ResultWrapper<Boolean>>()
    val changeProfileResult: LiveData<ResultWrapper<Boolean>>
        get() = _changeProfileResult

    fun getCurrentUser() = userRepo.getCurrentUser()

    fun changeProfile(fullName: String){
        viewModelScope.launch {
            userRepo.updateProfile(fullName).collect{
                _changeProfileResult.postValue(it)
            }
        }
    }

    fun createChangePasswordRequest(){
        userRepo.sendChangePasswordRequestByEmail()
    }

}