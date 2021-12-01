package com.blink.blinkshopping.ui.allcategory

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.databinding.LayoutDataBinding
import com.blink.blinkshopping.models.allcategorymodel.ChildrenXX

class InsideDataAdpater(
    private val context: Context,
    private val mutableList: MutableList<ChildrenXX>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyDataView(layoutData: LayoutDataBinding) :
        RecyclerView.ViewHolder(layoutData.root) {
        var binding: LayoutDataBinding
        init {
            this.binding = layoutData
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutDataBinding.inflate(layoutInflater, parent, false)
        return MyDataView(binding)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as MyDataView
        viewHolder.binding.data=mutableList[position]
    }
//    override fun onBindViewHolder(holder: MyDataView, position: Int) {
//        Glide.with(context)
//            .load(mutableList[position].image)
//            .into(holder.itemImage)
//        holder.itemName.text = mutableList[position].name
//    }

}