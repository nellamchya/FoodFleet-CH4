package com.shine.foodfleet.data.repository

import com.shine.foodfleet.data.dummy.DummyCategoryDataSource
import com.shine.foodfleet.data.local.database.datasource.MenuDataSource
import com.shine.foodfleet.data.local.database.mapper.toMenuList
import com.shine.foodfleet.model.Category
import com.shine.foodfleet.model.Menu
import com.shine.utils.ResultWrapper
import com.shine.utils.proceed
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

interface MenuRepository {
    fun getCategories(): List<Category>
    fun getMenus(): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val menuDataSource: MenuDataSource,
    private val dummyCategoryDataSource: DummyCategoryDataSource
) : MenuRepository {

    override fun getCategories(): List<Category> {
        return dummyCategoryDataSource.getMenuCategory()
    }

    override fun getMenus(): Flow<ResultWrapper<List<Menu>>> {
        return menuDataSource.getAllMenus().map {
            proceed { it.toMenuList() }
        }.onStart {
            emit(ResultWrapper.Loading())
            delay(2000)
        }
    }
}