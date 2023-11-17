package com.shine.foodfleet.data.network.api.datasource

import com.shine.foodfleet.data.network.api.model.category.CategoryItemResponse
import com.shine.foodfleet.data.network.api.model.menu.MenuResponse
import com.shine.foodfleet.data.network.api.model.order.OrderRequest
import com.shine.foodfleet.data.network.api.model.order.OrderResponse
import com.shine.foodfleet.data.network.api.service.FoodFleetApiService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FoodFleetApiDataSourceTest {

    @MockK
    lateinit var service: FoodFleetApiService
    private lateinit var dataSource: FoodFleetDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FoodFleetApiDataSource(service)
    }

    @Test
    fun getMenus() {
        runTest {
            val mockResponse = mockk<MenuResponse>(relaxed = true)
            coEvery { service.getMenus(any()) } returns mockResponse
            val response = dataSource.getMenus("makanan")
            coVerify { service.getMenus(any()) }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun getCategories() {
        runTest {
            val mockResponse = mockk<CategoryItemResponse>()
            coEvery { service.getCategories() } returns mockResponse
            val response = dataSource.getCategories()
            coVerify { service.getCategories() }
            assertEquals(response, mockResponse)
        }
    }

    @Test
    fun createOrder() {
        runTest {
            val mockResponse = mockk<OrderResponse>(relaxed = true)
            val mockRequest = mockk<OrderRequest>(relaxed = true)
            coEvery { service.createOrder(any()) } returns mockResponse
            val response = dataSource.createOrder(mockRequest)
            coVerify { service.createOrder(any()) }
            assertEquals(response, mockResponse)
        }
    }
}
