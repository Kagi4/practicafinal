package com.guillecarrasco.practicafinal.ui.fragments.product.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.guillecarrasco.practicafinal.databinding.ProductItemBinding
import com.guillecarrasco.practicafinal.models.Product


class ProductAdapter(private val mCallBack: OnClickedItemListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    
    interface OnClickedItemListener {
        fun onItemBuy(item: Product)
        fun onItemDetails(item: Product)
    }
    
    class ProductViewHolder(val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var items: MutableList<Product> = mutableListOf()
    
    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: Product) {
        items.add(item)
        notifyDataSetChanged()
    }    
   
    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Product>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductViewHolder(ProductItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            productNameTextView.text = item.title
            productPriceTextView.text = "${item.price}$"
            Glide.with(this.root.context)
                .load(item.thumbnail)
                .into(productImageView)
            buttonDetail.setOnClickListener {
                mCallBack.onItemDetails(item)
            }
            buttonBuy.setOnClickListener {
                mCallBack.onItemBuy(item)
            }
        }
    }

    override fun getItemCount() = items.size
}