package com.blink.blinkshopping.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.databinding.ItemStaggeredBinding
import com.blink.blinkshopping.models.AdsBlocks


/**
 * Created by Maruthi Ram Yadav on 10/29/2020.
 */
class StaggeredAdapter internal constructor(
    var context: Context,
    var adsBlocks: MutableList<AdsBlocks>,
    var onItemClick: ItemClickHandler
) : RecyclerView.Adapter<StaggeredAdapter.MyViewHolder>(){

    public class MyViewHolder(itemStaggeredBinding: ItemStaggeredBinding): RecyclerView.ViewHolder(
        itemStaggeredBinding.root
    ){
        var binding: ItemStaggeredBinding
        init {
            this.binding = itemStaggeredBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStaggeredBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return adsBlocks.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var size = adsBlocks.get(position).items!!.size
        var staggeredAdapter = StaggeredViewAdapter(context, adsBlocks.get(position).items,onItemClick)
        val layoutManager: GridLayoutManager
        layoutManager = GridLayoutManager(context, 2)
        holder.binding.rvAdsBlockInside.layoutManager = layoutManager
        layoutManager.spanSizeLookup = object : SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == size-1 && position and 1 == 0 ) 2 else 1
            }
        }
        holder.binding.rvAdsBlockInside.setHasFixedSize(true)
        holder.binding.rvAdsBlockInside.adapter = staggeredAdapter
    }

}
