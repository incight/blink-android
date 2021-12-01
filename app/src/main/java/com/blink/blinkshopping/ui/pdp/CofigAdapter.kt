package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.ProductsSkuByListQuery
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.databinding.*
import com.blink.blinkshopping.models.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class CofigAdapter(
    var mContext: Context,
    var baseArrayList: MutableList<ConfigurableOption>,
    var onItemClick: ItemClickHandler
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    ItemClickHandler {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemConfugurableValuesBinding.inflate(layoutInflater, parent, false)
        return OthersViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as OthersViewHolder
        viewHolder.binding.product = baseArrayList.get(position)
        //viewHolder.binding.txtValue.text = baseArrayList.get(position).label

        val configurableSubAdapter =
            ConfigSubAdapter(
                baseArrayList.get(position),
                mContext,
                onItemClick
            )
        val layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        viewHolder.binding.rvConfurable?.setLayoutManager(layoutManager)
        viewHolder.binding.rvConfurable?.adapter = configurableSubAdapter
        configurableSubAdapter.notifyDataSetChanged()


    }

    class OthersViewHolder(itemConfugurableValuesBinding: ItemConfugurableValuesBinding) :
        RecyclerView.ViewHolder(itemConfugurableValuesBinding.root) {
        var binding: ItemConfugurableValuesBinding

        init {
            this.binding = itemConfugurableValuesBinding
        }
    }


    override fun getItemCount(): Int {
        return baseArrayList.size
    }

    override fun onItemClick(position: String, item: ProductItem?, from: String) {
        //settingBottomItemDetails(,item)
    }

}
