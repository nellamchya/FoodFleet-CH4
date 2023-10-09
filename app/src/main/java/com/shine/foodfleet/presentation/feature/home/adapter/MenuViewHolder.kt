package com.shine.foodfleet.presentation.feature.home.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemMenuGridBinding
import com.shine.foodfleet.databinding.ItemMenuListBinding
import com.shine.foodfleet.model.Menu

class  ListMenuViewHolder(
    private val binding : ItemMenuListBinding,
    private val onClickListener : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(menu: Menu) {
        with(binding) {
            ivMenuImage.load(menu.imageResourceId)
            tvMenuName.text = menu.name
            tvMenuPrice.text = menu.price.toString()
            tvMenuRating.text = menu.rating.toString()
        }
        binding.root.setOnClickListener{
            onClickListener(menu)
        }
    }
}

class GridMenuViewHolder(
    private val binding : ItemMenuGridBinding,
    private val onClickListener : (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Menu> {
    override fun bind(menu: Menu) {
        with(binding) {
            ivMenuImage.load(menu.imageResourceId)
            tvMenuName.text = menu.name
            tvMenuPrice.text = menu.price.toString()
            tvMenuRating.text = menu.rating.toString()
        }
        binding.root.setOnClickListener{
            onClickListener(menu)
        }
    }
}