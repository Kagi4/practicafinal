package com.guillecarrasco.practicafinal.ui.fragments.cart.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.annotation.SuppressLint
import com.guillecarrasco.practicafinal.databinding.CartItemBinding
import com.guillecarrasco.practicafinal.models.Product


class CartAdapter() : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    class CartViewHolder(val binding: CartItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var items: MutableList<Product> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: Product) {
        items.add(item)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Product>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CartViewHolder(CartItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            textViewPrice.text = "${item.price}$"
            textViewTitle.text = item.title
        }
    }

    override fun getItemCount() = items.size
}