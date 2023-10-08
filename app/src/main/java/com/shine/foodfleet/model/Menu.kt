package com.shine.foodfleet.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val id: Int? = null,
    val name: String,
    val price: Double,
    @DrawableRes val imageResourceId: Int,
    val rating: Double,
    val description: String,
    val location: String
) : Parcelable
