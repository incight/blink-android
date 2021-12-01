package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.databinding.ItemDeliverOptionsBinding
import com.blink.blinkshopping.databinding.ItemInstalmentsBinding
import com.blink.blinkshopping.databinding.ItemOfferplatesBinding
import com.blink.blinkshopping.databinding.ItemStorePickupBinding
import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.util.Utils.DecimalLimitter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


/**
 * Created by Praveen on 30/12/2020.
 */

class InstallmentsAdapter(
        var context: Context,
        var installmentsList: MutableList<Installmentinfo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemInstalmentsBinding: ItemInstalmentsBinding) :

            RecyclerView.ViewHolder(itemInstalmentsBinding.root) {
        var binding: ItemInstalmentsBinding

        init {
            this.binding = itemInstalmentsBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemInstalmentsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return installmentsList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.instalments = installmentsList.get(position)

//        viewHolder.binding.tvMonths.text=installmentsList.get(position).time_interval.toLowerCase()
//        if (installmentsList.get(position).emi_calculation!=null)
//        viewHolder.binding.txtInstalTotal.text= DecimalLimitter(installmentsList.get(position).emi_calculation)+" KD monthly"
//        viewHolder.binding.textView16.text=installmentsList.get(position).emi_calculation

    }
}