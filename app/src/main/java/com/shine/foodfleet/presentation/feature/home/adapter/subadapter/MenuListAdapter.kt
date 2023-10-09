package com.shine.foodfleet.presentation.feature.home.adapter.subadapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemMenuGridBinding
import com.shine.foodfleet.databinding.ItemMenuListBinding
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.presentation.feature.home.adapter.AdapterLayoutMode
import com.shine.foodfleet.presentation.feature.home.adapter.GridMenuViewHolder
import com.shine.foodfleet.presentation.feature.home.adapter.ListMenuViewHolder
import com.shine.utils.toCurrencyFormat

class MenuListAdapter(
    private val itemClick: (Menu) -> Unit,
    private val adapterLayoutMode: AdapterLayoutMode
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Menu>() {
        override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem.name == newItem.name &&
                    oldItem.imageResourceId == newItem.imageResourceId &&
                    oldItem.price == newItem.price &&
                    oldItem.rating == newItem.rating &&
                    oldItem.description == newItem.description &&
                    oldItem.location == newItem.location
        }

        override fun areContentsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem == newItem
        }
    })

    fun submitData(data: List<Menu>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (adapterLayoutMode) {
            AdapterLayoutMode.GRID -> {
                GridMenuViewHolder(
                    binding = ItemMenuGridBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClickListener = itemClick
                )
            }
            AdapterLayoutMode.LINEAR -> {
                ListMenuViewHolder(
                    binding = ItemMenuListBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ),
                    onClickListener = itemClick
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = dataDiffer.currentList[position]
        when (holder) {
            is GridMenuViewHolder -> {
                holder.bind(item)
            }
            is ListMenuViewHolder -> {
                holder.bind(item)
            }
        }
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size
}