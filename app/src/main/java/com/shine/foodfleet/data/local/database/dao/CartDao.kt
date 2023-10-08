package com.shine.foodfleet.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.shine.foodfleet.data.local.database.entity.CartEntity
import com.shine.foodfleet.data.local.database.relation.CartMenuRelation
import java.util.concurrent.Flow


@Dao
interface CartDao {

    @Query("SELECT * FROM CARTS")
    fun getAllCarts(): kotlinx.coroutines.flow.Flow<List<CartMenuRelation>>

    @Query("SELECT * FROM CARTS WHERE id == :cartId")
    fun getCartById(cartId: Int): kotlinx.coroutines.flow.Flow<CartMenuRelation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarts(cart: List<CartEntity>)

    @Delete
    suspend fun deleteCart(cart: CartEntity): Int

    @Update
    suspend fun updateCart(cart: CartEntity): Int
}