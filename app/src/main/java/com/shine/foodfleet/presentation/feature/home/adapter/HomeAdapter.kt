package com.shine.foodfleet.presentation.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemEmptyViewHolderBinding
import com.shine.foodfleet.databinding.ItemSectionBannerHomeBinding
import com.shine.foodfleet.databinding.ItemSectionCategoryBinding
import com.shine.foodfleet.databinding.ItemSectionHeaderHomeBinding
import com.shine.foodfleet.databinding.ItemSectionMenuListBinding
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.presentation.feature.home.adapter.model.HomeSection
import com.shine.foodfleet.presentation.feature.home.adapter.viewholder.BannerSectionViewHolder
import com.shine.foodfleet.presentation.feature.home.adapter.viewholder.CategoriesSectionViewHolder
import com.shine.foodfleet.presentation.feature.home.adapter.viewholder.EmptyViewHolder
import com.shine.foodfleet.presentation.feature.home.adapter.viewholder.HeaderSectionViewHolder
import com.shine.foodfleet.presentation.feature.home.adapter.viewholder.MenusSectionViewHolder

class HomeAdapter(
    private val onMenuClicked: (Menu) -> Unit,
) : RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<HomeSection>() {
            override fun areItemsTheSame(
                oldItem: HomeSection,
                newItem: HomeSection
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: HomeSection,
                newItem: HomeSection
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    fun submitData(data: List<HomeSection>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_HEADER -> {
                HeaderSectionViewHolder(
                    ItemSectionHeaderHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                )
            }

            ITEM_TYPE_BANNER -> {
                BannerSectionViewHolder(
                    ItemSectionBannerHomeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ITEM_TYPE_CATEGORY_LIST -> {
                CategoriesSectionViewHolder(
                    ItemSectionCategoryBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            ITEM_TYPE_MENU_LIST -> {
                MenusSectionViewHolder(
                    ItemSectionMenuListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onMenuClicked
                )
            }

            else -> EmptyViewHolder(
                ItemEmptyViewHolderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<HomeSection>).bind(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun getItemViewType(position: Int): Int {
        return when (dataDiffer.currentList[position]) {
            HomeSection.HeaderSection -> ITEM_TYPE_HEADER
            HomeSection.BannerSection -> ITEM_TYPE_BANNER
            is HomeSection.CategoriesSection -> ITEM_TYPE_CATEGORY_LIST
            is HomeSection.ProductsSection -> ITEM_TYPE_MENU_LIST
        }
    }

    companion object {
        const val ITEM_TYPE_HEADER = 1
        const val ITEM_TYPE_BANNER = 2
        const val ITEM_TYPE_CATEGORY_LIST = 3
        const val ITEM_TYPE_MENU_LIST = 4
    }
}