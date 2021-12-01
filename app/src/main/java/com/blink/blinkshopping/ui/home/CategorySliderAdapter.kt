package com.blink.blinkshopping.ui.home

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.ItemCategoryItemLayoutBinding
import com.blink.blinkshopping.databinding.ItemCategorySibitemLayoutBinding
import com.blink.blinkshopping.models.ProductItem

/**
 * Created by Praveen on 11/11/2020.
 */

class CategorySliderAdapter(
    var context: Context,
    var productItems: MutableList<ProductItem>,
    var from: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SubProductViewHolder(itemCategoryItemLayoutBinding: ItemCategoryItemLayoutBinding) :

        RecyclerView.ViewHolder(itemCategoryItemLayoutBinding.root) {
        var binding: ItemCategoryItemLayoutBinding

        init {
            this.binding = itemCategoryItemLayoutBinding
        }
    }

    class SubSubProductViewHolder(itemCategorySibitemLayoutBinding: ItemCategorySibitemLayoutBinding) :

        RecyclerView.ViewHolder(itemCategorySibitemLayoutBinding.root) {
        var binding: ItemCategorySibitemLayoutBinding

        init {
            this.binding = itemCategorySibitemLayoutBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (from.equals(parent.resources.getString(R.string.sub_product))) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCategoryItemLayoutBinding.inflate(layoutInflater, parent, false)
            return SubProductViewHolder(
                binding
            )
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemCategorySibitemLayoutBinding.inflate(layoutInflater, parent, false)
            return SubSubProductViewHolder(
                binding
            )
        }
    }

    override fun getItemCount(): Int {
        return productItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (from) {
            context.resources.getString(R.string.sub_product) -> {
                val viewHolder: SubProductViewHolder = holder as SubProductViewHolder
                viewHolder.binding.product = productItems.get(position)

                viewHolder.binding.txtActualPrice.setPaintFlags(
                    viewHolder.binding.txtActualPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG
                )
            }
            context.resources.getString(R.string.sub_sub_product) -> {
                val viewHolder: SubSubProductViewHolder = holder as SubSubProductViewHolder
                viewHolder.binding.product = productItems.get(position)

                viewHolder.binding.txtActualPrice.setPaintFlags(
                    viewHolder.binding.txtActualPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG
                )
            }
        }
    }
}