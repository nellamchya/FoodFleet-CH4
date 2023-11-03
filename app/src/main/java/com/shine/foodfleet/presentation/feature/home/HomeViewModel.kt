package com.shine.foodfleet.presentation.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.shine.foodfleet.R
import com.shine.foodfleet.data.local.datastore.UserPreferenceDataSource
import com.shine.foodfleet.data.repository.MenuRepository
import com.shine.foodfleet.data.repository.UserRepository
import com.shine.foodfleet.model.Category
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.model.User
import com.shine.foodfleet.utils.AssetWrapper
import com.shine.foodfleet.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MenuRepository,
    private val userPreferenceDataSource: UserPreferenceDataSource,
    private val assetWrapper: AssetWrapper,
    private val userRepo: UserRepository
) : ViewModel() {

    private val _categories = MutableLiveData<ResultWrapper<List<Category>>>()
    val categories: LiveData<ResultWrapper<List<Category>>>
        get() = _categories

    private val _menus = MutableLiveData<ResultWrapper<List<Menu>>>()
    val menus: LiveData<ResultWrapper<List<Menu>>>
        get() = _menus

    val userLayoutMode = userPreferenceDataSource.getUserLayoutModePrefFlow().asLiveData(Dispatchers.IO)

    private val _getProfileResult = MutableLiveData<User?>()
    val getProfileResult: LiveData<User?>
        get() = _getProfileResult

    fun getProfileData() {
        val data = userRepo.getCurrentUser()
        _getProfileResult.postValue(data)
    }
    fun getCurrentUser() = userRepo.getCurrentUser()
    fun getCategories() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCategories().collect {
                _categories.postValue(it)
            }
        }
    }


    fun getMenus(category: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMenus(if (category == assetWrapper.getString(R.string.text_all)) null else category?.lowercase()).collect {
                _menus.postValue(it)
            }
        }
    }

    fun setUserLayoutMode(layoutMode: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceDataSource.setUserLayoutModePref(layoutMode)
        }
    }
}
