package com.blink.blinkshopping.ui.allcategory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.databinding.ItemShopbrandsBinding
import com.blink.blinkshopping.models.*

class ShopByBrandsAdpater(
    var context: Context,
    var megaTxt: MutableList<Brands1>
    //var itemClickHandler: ItemClickHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemShopbrandsBinding: ItemShopbrandsBinding) :
        RecyclerView.ViewHolder(itemShopbrandsBinding.root) {
        var binding: ItemShopbrandsBinding
        init {
            this.binding = itemShopbrandsBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemShopbrandsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return megaTxt!!.size
    }

    fun addItem(item: Brands1) {
        megaTxt.add(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.brands= megaTxt.get(position)
    }
}

