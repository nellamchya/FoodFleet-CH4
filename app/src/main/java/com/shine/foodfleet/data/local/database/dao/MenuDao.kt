package com.shine.foodfleet.data.local.database.dao

import com.shine.foodfleet.data.local.database.entity.MenuEntity

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {

    @Query("SELECT * FROM MENUS")
    fun getAllMenus(): Flow<List<MenuEntity>>

    @Query("SELECT * FROM MENUS WHERE id == :id")
    fun getByMenuId(id: Int): Flow<MenuEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMenu(menu: List<MenuEntity>)

    @Delete
    suspend fun deleteMenu(menu: MenuEntity): Int

    @Update
    suspend fun updateMenu(menu: MenuEntity): Int
}
