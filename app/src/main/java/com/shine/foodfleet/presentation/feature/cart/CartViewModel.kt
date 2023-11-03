package com.shine.foodfleet.presentation.feature.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shine.foodfleet.data.repository.CartRepository
import com.shine.foodfleet.model.Cart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CartViewModel(
    private val cartRepo: CartRepository
) : ViewModel() {
    val cartList = cartRepo.getCartData().asLiveData(Dispatchers.IO)

    fun decreaseCart(item: Cart) {
        viewModelScope.launch {
            cartRepo.decreaseCart(item).collect()
        }
    }
    fun increaseCart(item: Cart) {
        viewModelScope.launch {
            cartRepo.increaseCart(item).collect()
        }
    }
    fun deleteCart(item: Cart) {
        viewModelScope.launch {
            cartRepo.deleteCart(item).collect()
        }
    }
    fun setCartNotes(item: Cart) {
        viewModelScope.launch {
            cartRepo.setCartNotes(item).collect()
        }
    }
}
