package com.shine.foodfleet.data.network.api.service

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.shine.foodfleet.BuildConfig
import com.shine.foodfleet.data.network.api.model.category.CategoryItemResponse
import com.shine.foodfleet.data.network.api.model.menu.MenuResponse
import com.shine.foodfleet.data.network.api.model.order.OrderRequest
import com.shine.foodfleet.data.network.api.model.order.OrderResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit
interface FoodFleetApiService {
    @GET("listmenu")
    suspend fun getMenus(@Query("c") category: String? = null): MenuResponse

    @GET("category")
    suspend fun getCategories(): CategoryItemResponse

    @POST("order")
    suspend fun createOrder(@Body orderRequest: OrderRequest): OrderResponse

    companion object {
        @JvmStatic
        operator fun invoke(chucker: ChuckerInterceptor): FoodFleetApiService {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(chucker)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
            return retrofit.create(FoodFleetApiService::class.java)
        }
    }
}
