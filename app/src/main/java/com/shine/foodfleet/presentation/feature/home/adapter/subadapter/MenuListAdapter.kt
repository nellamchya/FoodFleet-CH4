package com.shine.foodfleet.presentation.feature.home.adapter.subadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemMenuGridBinding
import com.shine.foodfleet.databinding.ItemMenuListBinding
import com.shine.foodfleet.model.Menu
import com.shine.foodfleet.presentation.feature.home.adapter.viewholder.GridMenuItemViewHolder
import com.shine.foodfleet.presentation.feature.home.adapter.viewholder.LinearMenuItemViewHolder
import java.lang.IllegalArgumentException

class MenuListAdapter(
    var layoutMode: Int,
    private val onItemClick: (Menu) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val LINEAR_LAYOUT = 1
        const val GRID_LAYOUT = 2
    }

    private val dataDiffer = AsyncListDiffer(this,object : DiffUtil.ItemCallback<Menu>(){
        override fun areContentsTheSame(oldItem: Menu, newItem:Menu): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Menu, newItem: Menu): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    fun submitData(data : List<Menu>){
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            LINEAR_LAYOUT -> {
                LinearMenuItemViewHolder(
                    binding = ItemMenuListBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ),
                    onItemClick = onItemClick
                )
            }
            GRID_LAYOUT -> {
                GridMenuItemViewHolder(
                    binding = ItemMenuGridBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ),
                    onItemClick = onItemClick
                )
            }
            else -> {
                throw IllegalArgumentException("Invalid View Type")
            }
        }
    }

    override fun getItemCount(): Int {
        return dataDiffer.currentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Menu>).bind(dataDiffer.currentList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return layoutMode
    }

    fun refreshList(){
        notifyItemRangeChanged(0,dataDiffer.currentList.size)
    }

}