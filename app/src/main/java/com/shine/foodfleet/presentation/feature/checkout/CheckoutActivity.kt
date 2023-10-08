package com.shine.foodfleet.presentation.feature.checkout

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.shine.foodfleet.R
import com.shine.foodfleet.data.local.database.AppDatabase
import com.shine.foodfleet.data.local.database.datasource.CartDataSource
import com.shine.foodfleet.data.local.database.datasource.CartDatabaseDataSource
import com.shine.foodfleet.data.repository.CartRepository
import com.shine.foodfleet.data.repository.CartRepositoryImpl
import com.shine.foodfleet.databinding.ActivityCheckoutBinding
import com.shine.foodfleet.databinding.LayoutDialogSuccesBinding
import com.shine.foodfleet.presentation.common.adapter.CartListAdapter
import com.shine.foodfleet.presentation.feature.main.MainActivity
import com.shine.utils.GenericViewModelFactory
import com.shine.utils.proceedWhen

class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val database = AppDatabase.getInstance(this)
        val cartDao = database.cartDao()
        val cartDataSource = CartDatabaseDataSource(cartDao)
        val repo: CartRepository = CartRepositoryImpl(cartDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(repo))
    }

    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeData()
        setClickListener()
    }

    private fun setClickListener() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.clActionOrder.setOnClickListener {
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        val binding: LayoutDialogSuccesBinding =
            LayoutDialogSuccesBinding.inflate(layoutInflater)
        val dialogView = binding.root

        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
        val dialog = builder.create()

        binding.tvBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))

        }
        dialog.show()
    }

    private fun observeData() {
        viewModel.cartListOrder.observe(this) { it ->
            it.proceedWhen(
                doOnSuccess = {
                    binding.rvOrderList.isVisible = true
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.rvOrderList.apply {
                        layoutManager = LinearLayoutManager(this@CheckoutActivity)
                        adapter = this@CheckoutActivity.adapter
                    }
                    it.payload?.let { (carts, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvTotalPrice.text = true.toString()
                    }
                },
                doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.rvOrderList.isVisible = false
                },
                doOnError = { err ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = err.exception?.message.orEmpty()
                    binding.rvOrderList.isVisible = false
                },
                doOnEmpty = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.cart_empty)
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvOrderList.isVisible = false
                    it.payload?.let { (_, totalPrice) ->
                        binding.tvTotalPrice.text = true.toString()
                    }
                }

            )
        }
    }
}