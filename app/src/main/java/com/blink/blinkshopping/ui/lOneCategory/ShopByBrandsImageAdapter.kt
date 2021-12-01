package com.blink.blinkshopping.ui.lOneCategory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.BrandImageLayoutBinding
import com.blink.blinkshopping.databinding.LayoutGridDataBinding
import com.blink.blinkshopping.models.brandlist.Brands
import com.bumptech.glide.Glide

class ShopByBrandsImageAdapter(
    private val context: Context,
    private val mutableList: List<Brands>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(layoutDataBinding: BrandImageLayoutBinding) :
        RecyclerView.ViewHolder(layoutDataBinding.root) {
        var binding: BrandImageLayoutBinding
        init {
            this.binding = layoutDataBinding
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = BrandImageLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = mutableList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder , position: Int) {
        val viewHolder = holder as MyViewHolder
        viewHolder.binding.data = mutableList.get(position)
    }

}