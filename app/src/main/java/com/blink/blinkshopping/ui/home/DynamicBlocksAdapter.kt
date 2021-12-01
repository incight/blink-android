package com.blink.blinkshopping.ui.home

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.blink.blinkshopping.ProductsSkuByListQuery
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.ItemDynamicBlocksLayoutBinding
import com.blink.blinkshopping.databinding.ItemNewArrivalsLayoutBinding
import com.blink.blinkshopping.models.ProductItem
import com.blink.blinkshopping.models.ProductsDetailedModel
import com.blink.blinkshopping.util.Url
import kotlinx.android.synthetic.main.item_new_arrivals_layout.view.*

class DynamicBlocksAdapter(
    var list: MutableList<ProductItem>, var context: Context,
    var onItemClick: ItemClickHandler
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemDynamicBlocksLayoutBinding: ItemDynamicBlocksLayoutBinding) :
        RecyclerView.ViewHolder(itemDynamicBlocksLayoutBinding.root) {
        var binding: ItemDynamicBlocksLayoutBinding

        init {
            this.binding = itemDynamicBlocksLayoutBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDynamicBlocksLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.product = list.get(position)

        if (list.get(position) != null) {

            holder.binding.txtActualPrice.setPaintFlags(holder.binding.txtActualPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

            holder.binding.rlayout1.setOnClickListener { v ->
                onItemClick.onItemClick(list.get(position).sku, list.get(position), "DynamicBlock")
            }

        }

    }

}