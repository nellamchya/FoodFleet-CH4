package com.shine.foodfleet.presentation.feature.home.adapter.subadapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.shine.foodfleet.databinding.ItemMenuGridBinding
import com.shine.foodfleet.model.Menu
import com.shine.utils.toCurrencyFormat

class MenuListAdapter(private val itemClick: (Menu) -> Unit) :
    RecyclerView.Adapter<MenuListAdapter.ItemMenuViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<Menu>() {
            override fun areItemsTheSame(
                oldItem: Menu,
                newItem: Menu
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Menu,
                newItem: Menu
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    fun submitData(data: List<Menu>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMenuViewHolder {
        val binding = ItemMenuGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemMenuViewHolder(binding, itemClick)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ItemMenuViewHolder, position: Int) {
        holder.bindView(dataDiffer.currentList[position])
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size


    class ItemMenuViewHolder(
        private val binding: ItemMenuGridBinding,
        val itemClick: (Menu) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.N)
        fun bindView(item: Menu) {
            with(item) {
                binding.ivMenuImage.load(item.imageResourceId){
                    crossfade(true)
                }
                binding.tvMenuName.text = item.name
                binding.tvMenuPrice.text = item.price.toCurrencyFormat()
                binding.tvRatingMenu.text = item.rating.toString()
                binding.tvMenuDesc.text = item.description
                binding.tvMenuLocation.text = item.location
                itemView.setOnClickListener { itemClick(this) }
            }
        }

    }

}