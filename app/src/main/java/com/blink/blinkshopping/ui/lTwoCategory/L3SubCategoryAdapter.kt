package com.blink.blinkshopping.ui.lTwoCategory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.ItemNewArrivalsLayoutBinding
import com.blink.blinkshopping.databinding.LayoutSubCatDataBinding
import com.blink.blinkshopping.models.allcategorymodel.ChildrenXX
import com.blink.blinkshopping.ui.home.NewArrivalsAdapter
import com.bumptech.glide.Glide

class L3SubCategoryAdapter(
    private val context: Context,
    private val mutableList: MutableList<ChildrenXX>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class L3ViewHolder(layoutSubCatDataBinding: LayoutSubCatDataBinding) :
        RecyclerView.ViewHolder(layoutSubCatDataBinding.root) {
        var binding: LayoutSubCatDataBinding

        init {
            this.binding = layoutSubCatDataBinding
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutSubCatDataBinding.inflate(layoutInflater, parent, false)
        return L3ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: L3ViewHolder = holder as L3ViewHolder
        viewHolder.binding.data = mutableList.get(position)

//        Glide.with(context)
//            .load(mutableList[position].image)
//            .into(holder.itemImage)
//        holder.itemName.text = mutableList[position].name

    }
}