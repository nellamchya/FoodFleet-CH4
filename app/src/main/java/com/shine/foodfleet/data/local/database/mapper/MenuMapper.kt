package com.shine.foodfleet.data.local.database.mapper

import com.shine.foodfleet.data.local.database.entity.MenuEntity
import com.shine.foodfleet.model.Menu

fun MenuEntity?.toMenu() = Menu(
    id = this?.id ?: 0,
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    imageResourceId = this?.imageResourceId ?: 0,
    rating = this?.rating ?: 0.0,
    description = this?.description.orEmpty(),
    location = this?.location.orEmpty()
)

fun Menu?.toMenuEntity() = MenuEntity(
    name = this?.name.orEmpty(),
    price = this?.price ?: 0.0,
    imageResourceId = this?.imageResourceId ?: 0,
    description = this?.description.orEmpty(),
    rating = this?.rating ?: 0.0,
    location = this?.location.orEmpty()
)


fun List<MenuEntity?>.toMenuList() = this.map { it.toMenu() }
fun List<Menu?>.toMenuEntity() = this.map { it.toMenuEntity() }

