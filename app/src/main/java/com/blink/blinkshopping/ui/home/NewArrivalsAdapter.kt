package com.blink.blinkshopping.ui.home

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.ItemDealsOfDayLayoutBinding
import com.blink.blinkshopping.databinding.ItemNewArrivalsLayoutBinding
import com.blink.blinkshopping.models.ProductItem
import com.blink.blinkshopping.util.Url
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions


class NewArrivalsAdapter(
    var items: MutableList<ProductItem>, var context: Context,
    var onItemClick: ItemClickHandler, var from: String
) :  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemNewArrivalsLayoutBinding: ItemNewArrivalsLayoutBinding) :
        RecyclerView.ViewHolder(itemNewArrivalsLayoutBinding.root) {
        var binding: ItemNewArrivalsLayoutBinding

        init {
            this.binding = itemNewArrivalsLayoutBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemNewArrivalsLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.product = items.get(position)

        if (items.get(position) != null) {

            holder.binding.txtActualPrice.setPaintFlags(holder.binding.txtActualPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

            holder.binding.rlayout1.setOnClickListener { v ->
                onItemClick.onItemClick(items.get(position).sku, items.get(position), from)
            }

            holder.itemView.setOnLongClickListener(OnLongClickListener {
                holder.binding.rlayout1.visibility = GONE
                holder.binding.llayout2.visibility = VISIBLE
                true
            })

            holder.binding.imgIncrease.setOnClickListener(View.OnClickListener {
                var count: Int =
                    java.lang.String.valueOf(holder.binding.tvQuantity.getText()).toInt()
                count++
                holder.binding.tvQuantity.setText("" + count)
            })

            holder.binding.imgDecrease.setOnClickListener(View.OnClickListener {
                var count: Int =
                    java.lang.String.valueOf(holder.binding.tvQuantity.getText()).toInt()
                if (count == 1) {
                    holder.binding.tvQuantity.setText("1")
                } else {
                    count -= 1
                    holder.binding.tvQuantity.setText("" + count)
                }
            })
        }

    }


}