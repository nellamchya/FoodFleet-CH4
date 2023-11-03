package com.shine.foodfleet.model

import com.shine.foodfleet.data.network.api.model.order.OrderItemRequest

data class Cart(
    var id: Int? = null,
    var menuId: Int? = null,
    val menuName: String,
    var menuPrice: Int,
    val menuImgUrl: String,
    var itemQuantity: Int = 0,
    var itemNotes: String? = null
)

fun Cart.toOrderItemRequest() = OrderItemRequest(
    notes = this.itemNotes.orEmpty(),
    price = this.menuPrice,
    name = this.menuName,
    qty = this.itemQuantity
)

fun Collection<Cart>.toOrderItemRequestList() = this.map { it.toOrderItemRequest() }
