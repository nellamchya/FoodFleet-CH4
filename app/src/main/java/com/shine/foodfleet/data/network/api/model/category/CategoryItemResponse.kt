package com.shine.foodfleet.data.network.api.model.category

import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CategoryItemResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("data")
    val data: List<CategoryResponse>?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Boolean?
)