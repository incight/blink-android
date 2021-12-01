package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.ItemDeliverOptionsBinding
import com.blink.blinkshopping.databinding.ItemOfferplatesBinding
import com.blink.blinkshopping.databinding.ItemStorePickupBinding
import com.blink.blinkshopping.models.*
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


/**
 * Created by Praveen on 30/12/2020.
 */

class StorePickUpAdapter(
    var context: Context, var storePick: List<GetSourceStore>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemStorePickupBinding: ItemStorePickupBinding) :

        RecyclerView.ViewHolder(itemStorePickupBinding.root) {
        var binding: ItemStorePickupBinding

        init {
            this.binding = itemStorePickupBinding
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemStorePickupBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return storePick.size
    }

    fun selectedItem() {
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(clickListener: SingleClickListener?) {
        sClickListener = clickListener
    }

    companion object {
        private var sClickListener: SingleClickListener? = null
        private var sSelected = -1
    }

    interface SingleClickListener {
        fun onStoreItemClickListener(position: Int, from: String, shortForm: String)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.product = storePick.get(position)

        holder.binding.checkbox.isChecked = sSelected == position

        holder.itemView.setOnClickListener {
            sSelected = position

            sClickListener!!.onStoreItemClickListener(
                position,
                "Store Location",
                storePick.get(position).pickipfrom
            )

        }
    }
}