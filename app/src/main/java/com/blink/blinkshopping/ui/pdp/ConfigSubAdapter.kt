package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.ItemConfigSubvaluesBinding
import com.blink.blinkshopping.models.ConfigurableOption

class ConfigSubAdapter(
    var baseArrayList: ConfigurableOption,
    var context: Context,
    var onItemClick: ItemClickHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var index = -1
    var index1 = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemConfigSubvaluesBinding.inflate(layoutInflater, parent, false)
        return ColorsViewHolder(binding)
    }

    class ColorsViewHolder(itemConfigSubvaluesBinding: ItemConfigSubvaluesBinding) :
        RecyclerView.ViewHolder(itemConfigSubvaluesBinding.root) {
        var binding: ItemConfigSubvaluesBinding
        init {
            this.binding = itemConfigSubvaluesBinding
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder: ColorsViewHolder = holder as ColorsViewHolder

        if (baseArrayList.values.get(position).swatch_data!=null && baseArrayList.values.get(position).swatch_data.value.contains("#") ) {
            viewHolder.binding.cardview.visibility = View.VISIBLE
            viewHolder.binding.lLayout1.visibility = View.GONE


            viewHolder.binding.lLayout.setBackgroundColor(Color.parseColor(baseArrayList.values.get(position).swatch_data.value))
//            viewHolder.binding.cardview.setCardBackgroundColor(Color.parseColor(baseArrayList.values.get(position).swatch_data.value))
//            viewHolder.binding.cardview.radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12f, context.resources.displayMetrics)

            viewHolder.binding.lLayout.setOnClickListener {
                index1 = position
                notifyDataSetChanged()
            }
            if (index1 == position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.binding.lt.setBackgroundDrawable(context.resources.getDrawable(R.drawable.config_yellow, null))
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.binding.lt.setBackgroundDrawable(context.resources.getDrawable(R.drawable.config_plain, null))
                    holder.binding.cardview.setBackgroundDrawable(context.resources.getDrawable(R.drawable.config_plain, null))
                }
            }

        } else {

            viewHolder.binding.lLayout1.visibility = View.VISIBLE
            viewHolder.binding.cardview.visibility = View.GONE

            viewHolder.binding.txtValue.text = baseArrayList.values.get(position).default_label!!
            viewHolder.binding.lLayout1.setOnClickListener {
                index = position
                notifyDataSetChanged()
            }

            if (index == position) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.binding.lLayout1.setBackgroundDrawable(context.resources.getDrawable(R.drawable.yellow_bg, null))
                }
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    holder.binding.lLayout1.setBackgroundDrawable(context.resources.getDrawable(R.drawable.configurable_bg, null))
                }
            }

        }


    }

    override fun getItemCount(): Int {
        return baseArrayList.values.size
    }
}