package com.shine.foodfleet.presentation.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemSectionBannerHomeBinding
import com.shine.foodfleet.presentation.feature.home.adapter.model.HomeSection

class BannerSectionViewHolder(
    private val binding : ItemSectionBannerHomeBinding
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<HomeSection> {
    override fun bind(item: HomeSection) {
        if(item is HomeSection.BannerSection){
            //no-op
        }
    }
}