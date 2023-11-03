package com.shine.foodfleet.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.firebase.auth.FirebaseAuth
import com.shine.foodfleet.data.local.database.AppDatabase
import com.shine.foodfleet.data.local.database.datasource.CartDataSource
import com.shine.foodfleet.data.local.database.datasource.CartDatabaseDataSource
import com.shine.foodfleet.data.local.datastore.UserPreferenceDataSource
import com.shine.foodfleet.data.local.datastore.UserPreferenceDataSourceImpl
import com.shine.foodfleet.data.local.datastore.appDataStore
import com.shine.foodfleet.data.network.api.datasource.FoodFleetApiDataSource
import com.shine.foodfleet.data.network.api.datasource.FoodFleetDataSource
import com.shine.foodfleet.data.network.api.service.FoodFleetApiService
import com.shine.foodfleet.data.network.firebase.auth.FirebaseAuthDataSource
import com.shine.foodfleet.data.network.firebase.auth.FirebaseAuthDataSourceImpl
import com.shine.foodfleet.data.repository.CartRepository
import com.shine.foodfleet.data.repository.CartRepositoryImpl
import com.shine.foodfleet.data.repository.MenuRepository
import com.shine.foodfleet.data.repository.MenuRepositoryImpl
import com.shine.foodfleet.data.repository.UserRepository
import com.shine.foodfleet.data.repository.UserRepositoryImpl
import com.shine.foodfleet.presentation.feature.cart.CartViewModel
import com.shine.foodfleet.presentation.feature.checkout.CheckoutViewModel
import com.shine.foodfleet.presentation.feature.detailmenu.DetailMenuViewModel
import com.shine.foodfleet.presentation.feature.home.HomeViewModel
import com.shine.foodfleet.presentation.feature.login.LoginViewModel
import com.shine.foodfleet.presentation.feature.profile.ProfileViewModel
import com.shine.foodfleet.presentation.feature.profile.editprofile.EditProfileViewModel
import com.shine.foodfleet.presentation.feature.register.RegisterViewModel
import com.shine.foodfleet.presentation.feature.splashscreen.SplashViewModel
import com.shine.foodfleet.utils.AssetWrapper
import com.shine.foodfleet.utils.PreferenceDataStoreHelper
import com.shine.foodfleet.utils.PreferenceDataStoreHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val localModule = module {
        single { AppDatabase.getInstance(androidContext()) }
        single { get<AppDatabase>().cartDao() }
        single { androidContext().appDataStore }
        single<PreferenceDataStoreHelper> { PreferenceDataStoreHelperImpl(get()) }
    }

    private val networkModule = module {
        single { ChuckerInterceptor(androidContext()) }
        single { FoodFleetApiService.invoke(get()) }
        single { FirebaseAuth.getInstance() }
    }

    private val dataSourceModule = module {
        single<CartDataSource> { CartDatabaseDataSource(get()) }
        single<FoodFleetDataSource> { FoodFleetApiDataSource(get()) }
        single<UserPreferenceDataSource> { UserPreferenceDataSourceImpl(get()) }
        single<FirebaseAuthDataSource> { FirebaseAuthDataSourceImpl(get()) }
    }

    private val repositoryModule = module {
        single<CartRepository> { CartRepositoryImpl(get(), get()) }
        single<MenuRepository> { MenuRepositoryImpl(get()) }
        single<UserRepository> { UserRepositoryImpl(get()) }
    }

    private val utilsModule = module {
        single { AssetWrapper(androidContext()) }
    }

    private val viewModelModule = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::CartViewModel)
        viewModelOf(::SplashViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::RegisterViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::EditProfileViewModel)
        viewModel { DetailMenuViewModel(get(), get()) }
    }

    val modules: List<Module> = listOf(
        localModule,
        networkModule,
        dataSourceModule,
        repositoryModule,
        viewModelModule,
        utilsModule
    )
}
