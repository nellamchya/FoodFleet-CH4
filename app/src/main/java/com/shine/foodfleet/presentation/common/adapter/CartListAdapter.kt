package com.shine.foodfleet.presentation.common.adapter

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.shine.foodfleet.R
import com.shine.foodfleet.core.ViewHolderBinder
import com.shine.foodfleet.databinding.ItemCartMenuBinding
import com.shine.foodfleet.databinding.ItemCartMenuOrderBinding
import com.shine.foodfleet.model.Cart
import com.shine.foodfleet.model.CartMenu
import com.shine.utils.doneEditing
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo

class CartListAdapter(private val cartListener: CartListener? = null) :
    RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<CartMenu>() {
            override fun areItemsTheSame(
                oldItem: CartMenu,
                newItem: CartMenu
            ): Boolean {
                return oldItem.cart.id == newItem.cart.id && oldItem.menu.id == newItem.menu.id
            }

            override fun areContentsTheSame(
                oldItem: CartMenu,
                newItem: CartMenu
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        })

    fun submitData(data: List<CartMenu>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (cartListener != null) CartViewHolder(
            ItemCartMenuBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), cartListener
        ) else CartOrderViewHolder(
            ItemCartMenuOrderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<CartMenu>).bind(dataDiffer.currentList[position])
    }

}

class CartViewHolder(
    private val binding: ItemCartMenuBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {
    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: CartMenu) {
        with(binding) {
            binding.ivMenuImage.load(item.menu.imageResourceId) {
                crossfade(true)
            }
            tvMenuCount.text = item.cart.itemQuantity.toString()
            tvMenuName.text = item.menu.name
            tvMenuPrice.text = (item.cart.itemQuantity * item.menu.price).toString()
        }
    }

    private fun setCartNotes(item: CartMenu) {
        binding.etNotesItem.setText(item.cart.itemNotes)
        binding.etNotesItem.doneEditing {
            binding.etNotesItem.clearFocus()
            val newItem = item.cart.copy().apply {
                itemNotes = binding.etNotesItem.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: CartMenu) {
        with(binding) {
            ivMinus.setOnClickListener { cartListener?.onMinusTotalItemCartClicked(item.cart) }
            ivPlus.setOnClickListener { cartListener?.onPlusTotalItemCartClicked(item.cart) }
            ivRemoveCart.setOnClickListener { cartListener?.onRemoveCartClicked(item.cart) }
        }
    }
}

class CartOrderViewHolder(
    private val binding: ItemCartMenuOrderBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<CartMenu> {
    override fun bind(item: CartMenu) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: CartMenu) {
        with(binding) {
            binding.ivMenuImage.load(item.menu.imageResourceId) {
                crossfade(true)
            }
            tvTotalQuantity.text =
                itemView.rootView.context.getString(
                    R.string.total_quantity,
                    item.cart.itemQuantity.toString()
                )
            tvMenuName.text = item.menu.name
            tvMenuPrice.text = (item.cart.itemQuantity * item.menu.price).toString()
        }
    }

    private fun setCartNotes(item: CartMenu) {
        binding.tvNotes.text = item.cart.itemNotes
    }

}


interface CartListener {
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}