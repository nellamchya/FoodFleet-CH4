package com.shine.foodfleet.presentation.feature.detailmenu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import coil.load
import com.shine.foodfleet.R
import com.shine.foodfleet.databinding.ActivityDetailMenuBinding
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.utils.AssetWrapper
import com.shine.foodfleet.utils.proceedWhen
import com.shine.foodfleet.utils.toCurrencyFormat
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@Suppress("DEPRECATION")
class DetailMenuActivity : AppCompatActivity() {

    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModel {
        parametersOf(intent.extras ?: bundleOf())
    }

    private val assetWrapper: AssetWrapper by inject()

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
        binding.btOpenMaps.setOnClickListener {
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
        viewModel.addToCartResult.observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(this, assetWrapper.getString(R.string.text_add_cart_success), Toast.LENGTH_SHORT).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(this, it.exception?.message.orEmpty(), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun bindMenu(menu: Menu?) {
        menu?.let { item ->
            binding.ivMenuImage.load(item.menuImageUrl)
            binding.tvMenuName.text = item.menuName
            binding.tvMenuDesc.text = item.menuDescription
            binding.tvMenuPrice.text = item.menuPrice.toCurrencyFormat()
            binding.tvDescLocation.text = item.menuShopLocation
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
