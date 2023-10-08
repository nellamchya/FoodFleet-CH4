package com.shine.foodfleet.model

data class Cart(
    var id: Int = 0,
    var menuId : Int = 0,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null,
)