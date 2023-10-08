package com.shine.foodfleet.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menus")
data class MenuEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "image_resource_id")
    val imageResourceId:  Int,
    @ColumnInfo(name = "ratting")
    val rating: Double,
    @ColumnInfo(name = "location")
    val location: String
)