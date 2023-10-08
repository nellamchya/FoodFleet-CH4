package com.shine.foodfleet.presentation.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemSectionHeaderHomeBinding
import com.shine.foodfleet.presentation.feature.home.adapter.model.HomeSection


class HeaderSectionViewHolder(
    private val binding : ItemSectionHeaderHomeBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {
    override fun bind(item: HomeSection) {
        if(item is HomeSection.HeaderSection){
            //no-op
        }
    }
}

