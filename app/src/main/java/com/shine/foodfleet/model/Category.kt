package com.shine.foodfleet.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class Category(
    val categoryName: String,
    val categoryImage: String,
    val id: String = UUID.randomUUID().toString()
) : Parcelable
