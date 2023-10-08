package com.shine.foodfleet.presentation.feature.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import coil.load
import com.shine.foodfleet.data.local.database.AppDatabase
import com.shine.foodfleet.data.local.database.datasource.CartDataSource
import com.shine.foodfleet.data.local.database.datasource.CartDatabaseDataSource
import com.shine.foodfleet.data.repository.CartRepository
import com.shine.foodfleet.data.repository.CartRepositoryImpl
import com.shine.foodfleet.databinding.ActivityDetailMenuBinding
import com.shine.foodfleet.model.Menu
import com.shine.utils.GenericViewModelFactory
import com.shine.utils.proceedWhen
import com.shine.utils.toCurrencyFormat

class DetailMenuActivity : AppCompatActivity() {

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource: CartDataSource = CartDatabaseDataSource(cartDao)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(
            DetailMenuViewModel(intent?.extras, repo)
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        bindMenu(viewModel.menu)
        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btOpenMaps.setOnClickListener(){
            showLocation()
        }
        binding.ivMinus.setOnClickListener {
            viewModel.minus()
        }
        binding.ivPlus.setOnClickListener {
            viewModel.add()
        }
        binding.btnAddToCart.setOnClickListener {
            viewModel.addToCart()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun observeData() {
        viewModel.priceLiveData.observe(this) {
            binding.tvCalculatedMenuPrice.text = it.toCurrencyFormat()
        }
        viewModel.menuCountLiveData.observe(this) {
            binding.tvMenuCount.text = it.toString()
        }
        viewModel.addToCartResult.observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, "Add to cart success !", Toast.LENGTH_SHORT).show()
                    finish()
                }, doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                })
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun bindMenu(menu: Menu?) {
        menu?.let { item ->
            binding.ivMenuImage.load(item.imageResourceId) {
                crossfade(true)
            }
            binding.tvMenuName.text = item.name
            binding.tvMenuDesc.text = item.description
            binding.tvMenuPrice.text = item.price.toCurrencyFormat()
            binding.tvMenuRating.text = item.rating.toString()
            binding.tvDescLocation.text = item.location
        }
    }

    private fun showLocation() {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://maps.app.goo.gl/h4wQKqaBuXzftGK77")
        )
        startActivity(intent)
    }

    companion object {
        const val EXTRA_MENU = "EXTRA_MENU"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRA_MENU, menu)
            context.startActivity(intent)
        }
    }
}