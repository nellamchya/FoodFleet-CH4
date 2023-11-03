package com.shine.foodfleet.presentation.feature.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shine.foodfleet.data.repository.CartRepository
import com.shine.foodfleet.data.repository.UserRepository
import com.shine.foodfleet.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutViewModel(
    private val cartRepo: CartRepository,
    private val userRepo: UserRepository
) : ViewModel() {
    val cartListOrder = cartRepo.getCartData().asLiveData(Dispatchers.IO)

    private val _checkoutResult = MutableLiveData<ResultWrapper<Boolean>>()
    val checkoutResult: LiveData<ResultWrapper<Boolean>>
        get() = _checkoutResult

    fun order() {
        viewModelScope.launch(Dispatchers.IO) {
            val carts = cartListOrder.value?.payload?.first ?: return@launch
            cartRepo.order(carts).collect {
                _checkoutResult.postValue(it)
            }
        }
    }
    fun deleteAllCart() {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepo.deleteAllCart()
        }
    }
}
