package com.blink.blinkshopping.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.databinding.ItemBannerStaggered1Binding
import com.blink.blinkshopping.models.BannerId

/**
 * Created by praveen on 3/11/2020.
 */
class BannerStaggeredAdapter internal constructor(
    var context: Context,
    var item: MutableList<BannerId>,
    var onItemClick: ItemClickHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    public class ViewHolder(itemBannerStaggered1Binding: ItemBannerStaggered1Binding) :
        RecyclerView.ViewHolder(
            itemBannerStaggered1Binding.root
        ) {
        var binding: ItemBannerStaggered1Binding

        init {
            this.binding = itemBannerStaggered1Binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBannerStaggered1Binding.inflate(layoutInflater, parent, false)
        return ViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.bannerStaggeredView = item.get(position)

        holder.itemView.setOnClickListener { v ->
            onItemClick.onItemClick(item.get(position).url_banner, null, "banner")
        }

    }


}
