package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.databinding.ItemDeliverOptionsBinding
import com.blink.blinkshopping.models.DOption
import com.blink.blinkshopping.models.Deliveryoption


/**
 * Created by Praveen on 11/12/2020.
 */

class DeliveryOptionsAdapter(
    var context: Context,
    var offerDetails: Deliveryoption,
    var itemClickHandler: ItemClickHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemDeliveryOptionBinding: ItemDeliverOptionsBinding) :

        RecyclerView.ViewHolder(itemDeliveryOptionBinding.root) {
        var binding: ItemDeliverOptionsBinding

        init {
            this.binding = itemDeliveryOptionBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDeliverOptionsBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return offerDetails.options.size
    }

    fun addItem(item: DOption) {
        offerDetails.options.add(item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.productTitle.text =
            offerDetails.list().get(position).deliveryOptionsName()

//        viewHolder.binding.checkbox.setOnClickListener {
//            if (offerDetails.list().get(position).name.equals("Store pickup"))
//                itemClickHandler.onDeliveryOptionItemClick(
//                    offerDetails.list().get(position).id,
//                    "Store pickup",
//                    offerDetails.list().get(position).short_form
//                )
//            else
//                itemClickHandler.onDeliveryOptionItemClick(
//                    offerDetails.list().get(position).id,
//                    "Others",
//                    offerDetails.list().get(position).short_form
//                )
//
//        }
//
//        viewHolder.binding.checkbox.setOnCheckedChangeListener({ buttonView, isChecked ->
//            if (isChecked) {
//            } else {
//
//            }
//        })


        viewHolder.itemView.setOnClickListener {
            sSelected = position
            sClickListener!!.onItemClickListener(position, holder.itemView)
        }

    }

    fun selectedItem() {
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(clickListener: SingleClickListener?) {
        sClickListener = clickListener
    }

    interface SingleClickListener {
        fun onItemClickListener(position: Int, view: View?)
    }

    companion object {
        private var sClickListener: SingleClickListener? = null
        private var sSelected = -1
    }

}