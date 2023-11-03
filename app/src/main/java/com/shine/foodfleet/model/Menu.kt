package com.shine.foodfleet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: Int? = null,
    val menuName: String,
    val menuDescription: String,
    val menuPrice: Int,
    val menuFormattedPrice: String,
    val menuImageUrl: String,
    val menuShopLocation: String
) : Parcelable
