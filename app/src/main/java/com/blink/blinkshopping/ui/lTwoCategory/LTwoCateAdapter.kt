package com.blink.blinkshopping.ui.lTwoCategory

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.L2ProductClickHandler
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.CategoryLtwoLayoutBinding
import com.blink.blinkshopping.databinding.ItemNewArrivalsLayoutBinding
import com.blink.blinkshopping.models.allcategorymodel.ChildrenX
import com.blink.blinkshopping.ui.home.NewArrivalsAdapter
import com.bumptech.glide.Glide
import java.util.HashMap

class LTwoCateAdapter(
    private val context: Context,
    private val mutableList: List<ChildrenX>,
    var onL2ProItemClick: L2ProductClickHandler,
    var from: String,
    var itemListwise: HashMap<String, String>,
    var selectedProPosition: Int,
    var recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var selectedPosition = selectedProPosition

    class MyViewHolder(categoryLtwoLayoutBinding: CategoryLtwoLayoutBinding) :
        RecyclerView.ViewHolder(categoryLtwoLayoutBinding.root) {
        var binding: CategoryLtwoLayoutBinding

        init {
            this.binding = categoryLtwoLayoutBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CategoryLtwoLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mutableList.size
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         val viewHolder: MyViewHolder = holder as MyViewHolder
//       viewHolder.binding.product = items.get(position)

         recyclerView.smoothScrollToPosition(selectedPosition)

        if (selectedPosition == position) {
            holder.itemView.setBackgroundResource(R.drawable.selected_border_gray);
            holder.binding.tvItemName.setTextColor(context.resources.getColor(R.color.price_blue_color))
            //holder.tvItemName.typeface= Typeface.DEFAULT_BOLD
        } else {
            holder.itemView.setBackgroundResource(R.drawable.unselected_border);
            holder.binding.tvItemName.setTextColor(context.resources.getColor(R.color.gray_ltwo_color))
            // holder.tvItemName.typeface= Typeface.DEFAULT
        }

        holder.itemView.setOnClickListener {
            selectedPosition = position
            notifyDataSetChanged()

            onL2ProItemClick.onL2ProItemClick(
                mutableList.get(position).id.toString(),
                mutableList.get(position).children,
                from,
                itemListwise,
                position
            )
        }

        Glide.with(context)
            .load(mutableList[position].image)
            .into(holder.binding.ivItemImage)
        holder.binding.tvItemName.text = mutableList[position].name
    }


}


