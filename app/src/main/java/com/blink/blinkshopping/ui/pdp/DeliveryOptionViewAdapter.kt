package com.blink.blinkshopping.ui.pdp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.ItemConfugurableValuesBinding
import com.blink.blinkshopping.databinding.ItemDeliverOptionsBinding
import com.blink.blinkshopping.models.DOption
import com.blink.blinkshopping.models.Deliveryoption


class DeliveryOptionViewAdapter(private var offerDetails: Deliveryoption) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

//    class DataObjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var productTitle: TextView
//        var checkbox: RadioButton
//
//        init {
//            productTitle = itemView.findViewById<View>(R.id.productTitle) as TextView
//            checkbox = itemView.findViewById<View>(R.id.checkbox) as RadioButton
//        }
//    }
    class DataObjectHolder(itemDeliverOptionsBinding: ItemDeliverOptionsBinding) :
        RecyclerView.ViewHolder(itemDeliverOptionsBinding.root) {
        var binding: ItemDeliverOptionsBinding

        init {
            this.binding = itemDeliverOptionsBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemDeliverOptionsBinding.inflate(layoutInflater, parent, false)
        return DataObjectHolder(binding)
    }

    fun selectedItem() {
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(clickListener: SingleClickListener?) {
        sClickListener = clickListener
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val viewHolder = holder as DataObjectHolder
        //viewHolder.binding.product = offerDetails.list().get(position)


        holder.binding.productTitle.text = offerDetails.list().get(position).deliveryOptionsName()
        holder.binding.checkbox.isChecked = sSelected == position

        holder.itemView.setOnClickListener {
            sSelected = position

            if (offerDetails.list().get(position).name.equals("Store pickup"))
                sClickListener!!.onItemClickListener(
                    offerDetails.list().get(position).id,
                    "Store pickup",
                    offerDetails.list().get(position).short_form
                )
            else
                sClickListener!!.onItemClickListener(
                    offerDetails.list().get(position).id,
                    "Others",
                    offerDetails.list().get(position).short_form
                )

        }

    }

    override fun getItemCount(): Int {
        return offerDetails.options.size
    }

    fun addItem(item: DOption) {
        offerDetails.options.add(item)
        notifyDataSetChanged()
    }

    interface SingleClickListener {
        fun onItemClickListener(position: Int, from: String, shortForm: String)
    }

    companion object {
        private var sClickListener: SingleClickListener? = null
        private var sSelected = -1
    }

}