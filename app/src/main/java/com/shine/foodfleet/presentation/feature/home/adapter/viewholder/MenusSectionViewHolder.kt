package com.shine.foodfleet.presentation.feature.home.adapter.viewholder

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemSectionMenuListBinding
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.presentation.feature.home.adapter.model.HomeSection
import com.shine.foodfleet.presentation.feature.home.adapter.subadapter.MenuListAdapter
import com.shine.utils.GridSpacingItemDecoration
import com.shine.utils.proceedWhen


class MenusSectionViewHolder(
    private val binding: ItemSectionMenuListBinding,
    private val onClickListener: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {
    private val itemDecoration = GridSpacingItemDecoration(2, 48, true)
    init {
        binding.rvMenuList.addItemDecoration(itemDecoration)
    }
    private val adapter: MenuListAdapter by lazy {
        MenuListAdapter {
            onClickListener.invoke(it)
        }
    }

    override fun bind(item: HomeSection) {
        if (item is HomeSection.ProductsSection) {
            item.data.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.rvMenuList.apply {
                    isVisible = true
                    adapter = this@MenusSectionViewHolder.adapter
                }
                item.data.payload?.let { data -> adapter.submitData(data) }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.rvMenuList.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = item.data.exception?.message.orEmpty()
                binding.rvMenuList.isVisible = false
            })
        }
    }
}