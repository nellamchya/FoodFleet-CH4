package com.shine.foodfleet.presentation.feature.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemEmptyViewHolderBinding
import com.shine.foodfleet.presentation.feature.home.adapter.model.HomeSection


class EmptyViewHolder(private val binding : ItemEmptyViewHolderBinding) : ViewHolder(binding.root),
    ViewHolderBinder<HomeSection> {
    override fun bind(item: HomeSection) {
        //no-op
    }
}