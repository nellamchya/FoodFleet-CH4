package com.shine.foodfleet.presentation.feature.home.adapter.viewholder

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemMenuGridBinding
import com.shine.foodfleet.databinding.ItemMenuListBinding
import com.shine.foodfleet.model.Menu
import com.shine.utils.toCurrencyFormat

class LinearMenuItemViewHolder(
    private val binding: ItemMenuListBinding,
    private val onItemClick : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Menu> {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun bind (item : Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImage.load(item.menuImageUrl){
            crossfade(true)
        }
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = item.menuPrice.toCurrencyFormat()
    }
}

class GridMenuItemViewHolder(
    private val binding: ItemMenuGridBinding,
    private val onItemClick : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root),ViewHolderBinder<Menu> {
    override fun bind (item : Menu) {
        binding.root.setOnClickListener {
            onItemClick.invoke(item)
        }
        binding.ivMenuImage.load(item.menuImageUrl){
            crossfade(true)
        }
        binding.tvMenuName.text = item.menuName
        binding.tvMenuPrice.text = item.menuPrice.toCurrencyFormat()
    }
}