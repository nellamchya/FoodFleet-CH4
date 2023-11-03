package com.shine.foodfleet.data.network.api.datasource

import com.shine.foodfleet.data.network.api.model.category.CategoryItemResponse
import com.shine.foodfleet.data.network.api.model.menu.MenuResponse
import com.shine.foodfleet.data.network.api.model.order.OrderRequest
import com.shine.foodfleet.data.network.api.model.order.OrderResponse
import com.shine.foodfleet.data.network.api.service.FoodFleetApiService

interface FoodFleetDataSource {
    suspend fun getMenus(category: String? = null): MenuResponse

    suspend fun getCategories(): CategoryItemResponse

    suspend fun createOrder(orderRequest: OrderRequest): OrderResponse
}

class FoodFleetApiDataSource(private val service: FoodFleetApiService) : FoodFleetDataSource {
    override suspend fun getMenus(category: String?): MenuResponse {
        return service.getMenus(category)
    }

    override suspend fun getCategories(): CategoryItemResponse {
        return service.getCategories()
    }

    override suspend fun createOrder(orderRequest: OrderRequest): OrderResponse {
        return service.createOrder(orderRequest)
    }
}
