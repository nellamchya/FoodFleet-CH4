package com.shine.foodfleet.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shine.foodfleet.data.repository.MenuRepository
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.presentation.feature.home.adapter.model.HomeSection
import com.shine.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class HomeViewModel(private val repo: MenuRepository) : ViewModel() {

    val homeData : LiveData<List<HomeSection>>
        get() = repo.getMenus().map {
            mapToHomeData(it)
        }.asLiveData(Dispatchers.IO)

    private fun mapToHomeData(productResult : ResultWrapper<List<Menu>>): List<HomeSection> = listOf(
        HomeSection.HeaderSection,
        HomeSection.BannerSection,
        HomeSection.CategoriesSection(repo.getCategories()),
        HomeSection.ProductsSection(productResult),
    )
}