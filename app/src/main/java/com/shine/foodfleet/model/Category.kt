package com.shine.foodfleet.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    @DrawableRes val imageResourceId: Int,
    val name: String,
) : Parcelable