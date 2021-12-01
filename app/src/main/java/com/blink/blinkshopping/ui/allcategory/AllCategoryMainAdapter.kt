package com.blink.blinkshopping.ui.allcategory

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.CategoryLayoutBinding
import com.blink.blinkshopping.databinding.ItemNewArrivalsLayoutBinding
import com.blink.blinkshopping.models.allcategorymodel.AllCategoryData
import com.blink.blinkshopping.ui.home.NewArrivalsAdapter

class AllCategoryMainAdapter(val users: AllCategoryData) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedPosition = 0

    class ViewHolder(categorylayout: CategoryLayoutBinding) :
        RecyclerView.ViewHolder(categorylayout.root) {
        var binding: CategoryLayoutBinding
        init {
            this.binding = categorylayout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryLayoutBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = users.data.categoryList.get(0).children.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as ViewHolder

        val dataList = users.data.categoryList.get(0).children.get(position)

        viewHolder.binding.product=   dataList

            if (selectedPosition == position)
                holder.itemView.setBackgroundColor(Color.parseColor("#FFD500"))
            else
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))

            holder.itemView.setOnClickListener {
                selectedPosition = position
                notifyDataSetChanged()
            }
            holder.binding.tvCategory.text = dataList.name
    }

    }




