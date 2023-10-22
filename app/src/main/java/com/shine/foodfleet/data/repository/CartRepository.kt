package com.shine.foodfleet.data.repository

import com.shine.foodfleet.data.local.database.datasource.CartDataSource
import com.shine.foodfleet.data.local.database.entity.CartEntity
import com.shine.foodfleet.data.local.database.mapper.toCartEntity
import com.shine.foodfleet.data.local.database.mapper.toCartList
import com.shine.foodfleet.data.network.api.datasource.FoodFleetDataSource
import com.shine.foodfleet.data.network.api.model.order.OrderItemRequest
import com.shine.foodfleet.data.network.api.model.order.OrderRequest
import com.shine.foodfleet.model.Cart
import com.shine.foodfleet.model.Menu
import com.shine.utils.ResultWrapper
import com.shine.utils.proceed
import com.shine.utils.proceedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.lang.IllegalStateException


interface CartRepository {
    fun getCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>>
    suspend fun createCart(menu: Menu, totalQty: Int): Flow<ResultWrapper<Boolean>>
    suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>>
    suspend fun deleteAllCart()
    suspend fun order(items: List<Cart>): Flow<ResultWrapper<Boolean>>
}

class CartRepositoryImpl(
    private val cartDataSource: CartDataSource,
    private val foodFleetDataSource: FoodFleetDataSource
) : CartRepository {
    override fun getCartData(): Flow<ResultWrapper<Pair<List<Cart>, Int>>> {
        return cartDataSource.getAllCarts()
            .map {
                proceed {
                    val result = it.toCartList()
                    val totalPrice = result.sumOf {
                        val qty = it.itemQuantity
                        val pricePerItem = it.menuPrice
                        qty * pricePerItem
                    }
                    Pair(result, totalPrice)
                }
            }.map {
                if (it.payload?.first?.isEmpty() == true)
                    ResultWrapper.Empty(it.payload)
                else
                    it
            }.onStart {
                emit(ResultWrapper.Loading())
                delay(2000)
            }
    }

    override suspend fun createCart(menu: Menu, totalQty: Int): Flow<ResultWrapper<Boolean>> {
        return menu.id?.let{ menuId ->
            proceedFlow {
                val affectedRow = cartDataSource.insertCart(
                    CartEntity(
                        menuId = menuId,
                        itemQuantity = totalQty,
                        menuImgUrl = menu.menuImageUrl,
                        menuPrice =  menu.menuPrice,
                        menuName = menu.menuName
                    )
                )
                affectedRow > 0
            }
        } ?: flow {
            emit(ResultWrapper.Error(IllegalStateException("Menu ID not found")))
        }
    }

    override suspend fun decreaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity -= 1
        }
        return if (modifiedCart.itemQuantity <= 0) {
            proceedFlow { cartDataSource.deleteCart(modifiedCart.toCartEntity()) > 0 }
        } else {
            proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
        }
    }

    override suspend fun increaseCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        val modifiedCart = item.copy().apply {
            itemQuantity += 1
        }
        return proceedFlow { cartDataSource.updateCart(modifiedCart.toCartEntity()) > 0 }
    }

    override suspend fun setCartNotes(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.updateCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteCart(item: Cart): Flow<ResultWrapper<Boolean>> {
        return proceedFlow { cartDataSource.deleteCart(item.toCartEntity()) > 0 }
    }

    override suspend fun deleteAllCart() {
        return cartDataSource.deleteAllCartItems()
    }

    override suspend fun order(items: List<Cart>): Flow<ResultWrapper<Boolean>> {
        return proceedFlow {
            val orderItems = items.map {
                OrderItemRequest(it.menuName, it.itemQuantity, it.itemNotes, it.menuPrice)
            }
            val orderRequest = OrderRequest(orderItems)
            foodFleetDataSource.createOrder(orderRequest).status == true
        }
    }

}

