package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.databinding.ItemOfferplatesBinding
import com.blink.blinkshopping.models.OData
import com.blink.blinkshopping.models.ProductItem
import com.blink.blinkshopping.util.Url
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


/**
 * Created by Praveen on 11/12/2020.
 */

class OfferPlatesAdapter(
    var context: Context,
    var offerDetails: OData,/*var onItemClick: ItemClickHandler,*/
    var from: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemOfferplatesBinding: ItemOfferplatesBinding) :
        RecyclerView.ViewHolder(itemOfferplatesBinding.root) {
        var binding: ItemOfferplatesBinding

        init {
            this.binding = itemOfferplatesBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemOfferplatesBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return offerDetails.offerplates.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.data = offerDetails.offerplates.get(position)

        viewHolder.binding.offerLink.setPaintFlags(viewHolder.binding.offerLink.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG)

        viewHolder.binding.offerLink.setOnClickListener {
            if (offerDetails.offerplates.get(position).link != null &&
                offerDetails.offerplates.get(position).link != "#"
            ) {
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(offerDetails.offerplates.get(position).link)
                context.startActivity(i)
            }
        }
    }
}