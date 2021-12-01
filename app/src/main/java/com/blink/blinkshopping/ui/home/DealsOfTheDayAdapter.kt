package com.blink.blinkshopping.ui.home

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.blink.blinkshopping.ProductsSkuByListQuery
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.ItemDealsOfDayLayoutBinding
import com.blink.blinkshopping.databinding.ItemStorePickupBinding
import com.blink.blinkshopping.databinding.LayoutDealsOfDayBinding
import com.blink.blinkshopping.models.GetSourceStore
import com.blink.blinkshopping.models.ProductItem
import com.blink.blinkshopping.models.ProductsDetailedModel
import com.blink.blinkshopping.ui.pdp.StorePickUpAdapter
import com.blink.blinkshopping.util.Url

class DealsOfTheDayAdapter(var items: MutableList<ProductItem>,var context: Context,
                           val onDodItemClicked: OnItemClickListener,
                           var onItemClick: ItemClickHandler,
                           var binding: LayoutDealsOfDayBinding)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var index = 0

    class ViewHolder(itemDealsOfDayLayoutBinding: ItemDealsOfDayLayoutBinding) :
        RecyclerView.ViewHolder(itemDealsOfDayLayoutBinding.root) {
        var binding: ItemDealsOfDayLayoutBinding
        init {
            this.binding = itemDealsOfDayLayoutBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDealsOfDayLayoutBinding.inflate(layoutInflater, parent, false)
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

            holder.binding.rlayout1.setOnClickListener(View.OnClickListener {
                onDodItemClicked.onDodItemClicked(items.get(position).sku, items.get(position),binding)
                index = position
                notifyDataSetChanged()
                onItemClick.onItemClick(items.get(position).sku, items.get(position), "deals_of_the_day")
            })

            if (index == position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.binding.rlayout1.setBackgroundDrawable(context.resources.getDrawable(R.drawable.ic_blue_selected, null))
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.binding.rlayout1.setBackgroundDrawable(context.resources.getDrawable(R.drawable.ic_grey_unselected, null))
                }
            }

        }

    }

    interface OnItemClickListener {
        fun onDodItemClicked(sku: String, item: ProductItem?,binding: LayoutDealsOfDayBinding)
    }
}


