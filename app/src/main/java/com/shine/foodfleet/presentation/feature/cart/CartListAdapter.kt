package com.shine.foodfleet.presentation.feature.cart

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
import com.shine.utils.doneEditing
import android.view.LayoutInflater
import android.view.ViewGroup
import com.shine.utils.toCurrencyFormat

class CartListAdapter(
    private val cartListener: CartListener? = null
) : RecyclerView.Adapter<ViewHolder>() {

    private val dataDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Cart>(){
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (cartListener != null) CartViewHolder(
            ItemCartMenuBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), cartListener
        ) else CartCheckoutViewHolder(
            ItemCartMenuOrderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolderBinder<Cart>).bind(dataDiffer.currentList[position])
    }

    fun setData(data: List<Cart>) {
        dataDiffer.submitList(data)
    }

}

class CartViewHolder(
    private val binding : ItemCartMenuBinding,
    private val cartListener: CartListener?
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart>{
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
        setClickListeners(item)
    }

    private fun setCartData(item: Cart) {
        with(binding){
            ivMenuImage.load(item.menuImgUrl){
                crossfade(true)
            }
            tvMenuName.text = item.menuName
            tvMenuPrice.text = (item.itemQuantity * item.menuPrice).toCurrencyFormat()
            tvMenuCount.text = item.itemQuantity.toString()

        }
    }

    private fun setCartNotes(item: Cart) {
        binding.etNotesItem.setText(item.itemNotes)
        binding.etNotesItem.doneEditing{
            binding.etNotesItem.clearFocus()
            val newItem = item.copy().apply {
                itemNotes = binding.etNotesItem.text.toString().trim()
            }
            cartListener?.onUserDoneEditingNotes(newItem)
        }
    }

    private fun setClickListeners(item: Cart) {
        with(binding){
            ivMinus.setOnClickListener {cartListener?.onMinusTotalItemCartClicked(item)}
            ivPlus.setOnClickListener {cartListener?.onPlusTotalItemCartClicked(item)}
            ivRemoveCart.setOnClickListener {cartListener?.onRemoveCartClicked(item)}
        }
    }

}

class CartCheckoutViewHolder(
    private val binding: ItemCartMenuOrderBinding,
) : RecyclerView.ViewHolder(binding.root), ViewHolderBinder<Cart>{
    override fun bind(item: Cart) {
        setCartData(item)
        setCartNotes(item)
    }

    private fun setCartData(item: Cart) {
        with(binding){
            ivMenuImage.load(item.menuImgUrl){
                crossfade(true)
            }
            tvMenuName.text = item.menuName
            tvMenuPrice.text = (item.itemQuantity * item.menuPrice).toCurrencyFormat()
            tvTotalQuantity.text =
                itemView.rootView.context.getString(
                    R.string.total_quantity,
                    item.itemQuantity.toString()
                )
        }
    }

    private fun setCartNotes(item: Cart){
        binding.tvNotes.text = item.itemNotes
    }

}

interface CartListener{
    fun onPlusTotalItemCartClicked(cart: Cart)
    fun onMinusTotalItemCartClicked(cart: Cart)
    fun onRemoveCartClicked(cart: Cart)
    fun onUserDoneEditingNotes(cart: Cart)
}