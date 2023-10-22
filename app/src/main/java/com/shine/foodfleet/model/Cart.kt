package com.shine.foodfleet.model

data class Cart(
    var id: Int? = null,
    var menuId: Int? = null,
    val menuName: String,
    var menuPrice: Int,
    val menuImgUrl: String,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null,
)