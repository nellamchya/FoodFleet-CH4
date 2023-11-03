package com.shine.foodfleet.data.network.api.model.menu

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.shine.foodfleet.model.Menu

@Keep
data class MenuItemResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("nama")
    val name: String?,
    @SerializedName("harga_format")
    val formattedPrice: String?,
    @SerializedName("harga")
    val price: Int?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("detail")
    val desc: String?,
    @SerializedName("alamat_resto")
    val address: String?
)

fun MenuItemResponse.toMenu() = Menu(
    id = this.id,
    menuName = this.name.orEmpty(),
    menuDescription = this.desc.orEmpty(),
    menuPrice = this.price ?: 0,
    menuFormattedPrice = this.formattedPrice.orEmpty(),
    menuImageUrl = this.imageUrl.orEmpty(),
    menuShopLocation = this.address.orEmpty()
)

fun Collection<MenuItemResponse>.toMenuList() = this.map { it.toMenu() }
