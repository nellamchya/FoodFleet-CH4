package com.shine.foodfleet.presentation.feature.home.adapter.model

import com.shine.foodfleet.model.Category
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.presentation.feature.home.adapter.HomeAdapter
import com.shine.utils.ResultWrapper


sealed class HomeSection(val id : Int) {
    data object HeaderSection : HomeSection(HomeAdapter.ITEM_TYPE_HEADER)
    data object BannerSection : HomeSection(HomeAdapter.ITEM_TYPE_BANNER)
    data class CategoriesSection(val data : List<Category>) : HomeSection(HomeAdapter.ITEM_TYPE_CATEGORY_LIST)
    data class ProductsSection(val data : ResultWrapper<List<Menu>>) : HomeSection(HomeAdapter.ITEM_TYPE_PRODUCT_LIST)
}
