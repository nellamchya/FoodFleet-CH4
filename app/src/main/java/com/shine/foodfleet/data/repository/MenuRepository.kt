package com.shine.foodfleet.data.repository

import com.shine.foodfleet.data.network.api.datasource.FoodFleetDataSource
import com.shine.foodfleet.data.network.api.model.category.toCategoryList
import com.shine.foodfleet.data.network.api.model.menu.toMenuList
import com.shine.foodfleet.model.Category
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.utils.ResultWrapper
import com.shine.foodfleet.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    suspend fun getCategories(): Flow<ResultWrapper<List<Category>>>
    suspend fun getMenus(category: String? = null): Flow<ResultWrapper<List<Menu>>>
}

class MenuRepositoryImpl(
    private val apiDataSource: FoodFleetDataSource
) : MenuRepository {
    override suspend fun getCategories(): Flow<ResultWrapper<List<Category>>> {
        return proceedFlow {
            apiDataSource.getCategories().data?.toCategoryList() ?: emptyList()
        }
    }

    override suspend fun getMenus(category: String?): Flow<ResultWrapper<List<Menu>>> {
        return proceedFlow {
            apiDataSource.getMenus(category).data?.toMenuList() ?: emptyList()
        }
    }
}
