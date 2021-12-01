package com.blink.blinkshopping.ui.lOneCategory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.L2ProductClickHandler
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.LayoutDataBinding
import com.blink.blinkshopping.databinding.LayoutGridDataBinding
import com.blink.blinkshopping.databinding.LevelOneLayoutBinding
import com.blink.blinkshopping.models.gridcategory.Children
import com.bumptech.glide.Glide


class GridLOneAdapter(
    private val context: Context,
    private val childrenList: List<Children>,
    private val rvGridView: RecyclerView,
    private val id: Int, var itemListwise: HashMap<String, String>,
    var onL2ProItemClick: L2ProductClickHandler, var from: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectedPosition = -1
    private var isTrue: Boolean = false

//    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var itemImage: ImageView = itemView.findViewById(R.id.ivItemImage)
//        var itemName: TextView = itemView.findViewById(R.id.tvItemName)
//    }

    class GridViewHolder(layoutDataBinding: LayoutGridDataBinding) :
        RecyclerView.ViewHolder(layoutDataBinding.root) {
        var binding: LayoutGridDataBinding

        init {
            this.binding = layoutDataBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LayoutGridDataBinding.inflate(layoutInflater, parent, false)
        return GridViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return childrenList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as GridViewHolder
       // viewHolder.binding.data = childrenList.get(position)
        if (id == childrenList[position].id) {
            selectedPosition = position
        }

        if (selectedPosition == position)
            holder.itemView.setBackgroundResource(R.drawable.selected_border);
        else
            holder.itemView.setBackgroundResource(R.drawable.unselected_border);

        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()
            onL2ProItemClick.onL2ProItemClick(
                childrenList.get(position).id.toString(),
                null,
                from,
                itemListwise,
                position
            )
        }
        Glide.with(context)
            .load(childrenList[position].image)
            .into(holder.binding.ivItemImage)
        holder.binding.tvItemName.text = childrenList[position].name
    }
}