package com.blink.blinkshopping.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.databinding.ItemMegamenuBinding
import com.blink.blinkshopping.models.*

/**
 * Created by Maruthi Ram Yadav on 11/11/2020.
 */

class MegaMenuAdapter(
    var context: Context,
    var megaTxt: MData,
    var itemClickHandler: ItemClickHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SubProductViewHolder(itemMegamenuBinding: ItemMegamenuBinding) :

        RecyclerView.ViewHolder(itemMegamenuBinding.root) {
        var binding: ItemMegamenuBinding

        init {
            this.binding = itemMegamenuBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMegamenuBinding.inflate(layoutInflater, parent, false)
        return SubProductViewHolder(
            binding
        )
    }

    override fun getItemCount(): Int {
        return megaTxt!!.megaMenuLinks!!.size
    }

    fun addItem(item: MegaMenuLink) {
        megaTxt.megaMenuLinks.add(0,item)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: SubProductViewHolder = holder as SubProductViewHolder
        viewHolder.binding.megaMenu = megaTxt!!.megaMenuLinks!!.get(position)
        viewHolder.binding.txtmega.setOnClickListener { v ->
            itemClickHandler.onItemClick("2", null, "mega_menu")
        }

    }
}
