package com.shine.foodfleet.presentation.feature.home.adapter.viewholder

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shine.foodfleet.R
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemSectionMenuListBinding
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.presentation.feature.home.adapter.AdapterLayoutMode
import com.shine.foodfleet.presentation.feature.home.adapter.model.HomeSection
import com.shine.foodfleet.presentation.feature.home.adapter.subadapter.MenuListAdapter
import com.shine.utils.GridSpacingItemDecoration
import com.shine.utils.proceedWhen


class MenusSectionViewHolder(
    private val binding: ItemSectionMenuListBinding,
    private val onClickListener: (Menu) -> Unit
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {
    private val itemDecoration = GridSpacingItemDecoration(2, 48, true)
    private val adapter: MenuListAdapter by lazy {
        MenuListAdapter(onClickListener, AdapterLayoutMode.GRID)
    }
    private val listAdapter: MenuListAdapter by lazy {
        MenuListAdapter(onClickListener, AdapterLayoutMode.LINEAR)
    }
    private var isGridMode = true

    init {
        binding.rvMenuItemGrid.addItemDecoration(itemDecoration)
        toggleLayout()
        binding.ivGrid.setOnClickListener {
            toggleLayout()
        }
    }

    private fun toggleLayout() {
        isGridMode = !isGridMode

        if (isGridMode) {
            binding.rvMenuItemList.layoutManager =
                LinearLayoutManager(binding.root.context)
            binding.rvMenuItemList.adapter = adapter
            binding.rvMenuItemList.visibility = View.VISIBLE
            binding.rvMenuItemGrid.visibility = View.GONE
            binding.ivGrid.setImageResource(R.drawable.ic_grid) // Ganti ikon tombol menjadi list
        } else {
            binding.rvMenuItemGrid.layoutManager =
                GridLayoutManager(binding.root.context, 2)
            binding.rvMenuItemGrid.adapter = listAdapter
            binding.rvMenuItemGrid.visibility = View.VISIBLE
            binding.rvMenuItemList.visibility = View.GONE
            binding.ivGrid.setImageResource(R.drawable.ic_list) // Ganti ikon tombol menjadi grid
        }

    }



    override fun bind(item: HomeSection) {
        if (item is HomeSection.ProductsSection) {
            item.data.proceedWhen(doOnSuccess = {
                binding.layoutState.root.isVisible = false
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = false
                binding.rvMenuItemGrid.apply {
                    isVisible = true
                    adapter = this@MenusSectionViewHolder.adapter
                }
                item.data.payload?.let { data -> adapter.submitData(data) }
            }, doOnLoading = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = true
                binding.layoutState.tvError.isVisible = false
                binding.rvMenuItemGrid.isVisible = false
            }, doOnError = {
                binding.layoutState.root.isVisible = true
                binding.layoutState.pbLoading.isVisible = false
                binding.layoutState.tvError.isVisible = true
                binding.layoutState.tvError.text = item.data.exception?.message.orEmpty()
                binding.rvMenuItemGrid.isVisible = false
            })
        }
    }
}