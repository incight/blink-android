package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.databinding.ItemFrequentBroughtBinding
import com.blink.blinkshopping.databinding.ItemOfferplatesBinding
import com.blink.blinkshopping.models.ProductItem

/**
 * Created by Praveen on 11/12/2020.
 */

class FrequentlyBroughtAdapter(
    var context: Context,
    var productItem: MutableList<ProductItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemFrequentBroughtBinding: ItemFrequentBroughtBinding) :

        RecyclerView.ViewHolder(itemFrequentBroughtBinding.root) {
        var binding: ItemFrequentBroughtBinding

        init {
            this.binding = itemFrequentBroughtBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFrequentBroughtBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productItem.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder as ViewHolder
        viewHolder.binding.product = productItem.get(position)

    }
}