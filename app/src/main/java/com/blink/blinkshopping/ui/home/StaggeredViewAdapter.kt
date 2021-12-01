package com.blink.blinkshopping.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.databinding.*
import com.blink.blinkshopping.models.AdsItems

class StaggeredViewAdapter(
    var context: Context,
    var item: MutableList<AdsItems>,
    var onItemClick: ItemClickHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class StaggeredViewHolder1(itemViewStaggered1Binding: ItemViewStaggered1Binding) :
        RecyclerView.ViewHolder(itemViewStaggered1Binding.root) {
        var binding: ItemViewStaggered1Binding

        init {
            this.binding = itemViewStaggered1Binding
        }
    }

    class StaggeredViewHolder2(itemViewStaggered2Binding: ItemViewStaggered2Binding) :
        RecyclerView.ViewHolder(itemViewStaggered2Binding.root) {
        var binding: ItemViewStaggered2Binding

        init {
            this.binding = itemViewStaggered2Binding
        }
    }

    class StaggeredViewHolder3(itemViewStaggered3Binding: ItemViewStaggered3Binding) :
        RecyclerView.ViewHolder(itemViewStaggered3Binding.root) {
        var binding: ItemViewStaggered3Binding

        init {
            this.binding = itemViewStaggered3Binding
        }
    }

    class StaggeredViewHolder4(itemViewStaggered4Binding: ItemViewStaggered4Binding) :
        RecyclerView.ViewHolder(itemViewStaggered4Binding.root) {
        var binding: ItemViewStaggered4Binding

        init {
            this.binding = itemViewStaggered4Binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (item.size == 1) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemViewStaggered1Binding.inflate(layoutInflater, parent, false)
            return StaggeredViewHolder1(binding)
        } else if (item.size == 2) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemViewStaggered2Binding.inflate(layoutInflater, parent, false)
            return StaggeredViewHolder2(binding)
        } else if (item.size == 3) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemViewStaggered3Binding.inflate(layoutInflater, parent, false)
            return StaggeredViewHolder3(binding)
        } else {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemViewStaggered4Binding.inflate(layoutInflater, parent, false)
            return StaggeredViewHolder4(binding)
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (item.size == 1) {
            val viewHolder: StaggeredViewHolder1 = holder as StaggeredViewHolder1
            viewHolder.binding.staggeredView = item.get(position)
            holder.binding.image.setOnClickListener {
                onItemClick.onItemClick(item.get(position).ads_block_image_id, null, "ads_block")
            }
        } else if (item.size == 2) {
            val viewHolder: StaggeredViewHolder2 = holder as StaggeredViewHolder2
            viewHolder.binding.staggeredView = item.get(position)
            holder.binding.image.setOnClickListener {
                onItemClick.onItemClick(item.get(position).ads_block_image_id, null, "ads_block")
            }
        } else if (item.size == 3) {
            val viewHolder: StaggeredViewHolder3 = holder as StaggeredViewHolder3
            viewHolder.binding.staggeredView = item.get(position)
            holder.binding.image.setOnClickListener {
                onItemClick.onItemClick(item.get(position).ads_block_image_id, null, "ads_block")
            }
        } else {
            val viewHolder: StaggeredViewHolder4 = holder as StaggeredViewHolder4
            viewHolder.binding.staggeredView = item.get(position)
            holder.binding.image.setOnClickListener {
                onItemClick.onItemClick(item.get(position).ads_block_image_id, null, "ads_block")
            }
        }

    }

//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        var path = ""
//        if (item.get(position).image!!.contains("https")) {
//            path = ""
//        } else {
//            path = Url
//        }
//        Glide.with(context)
//            .load(path + item.get(position).image)
//            .transition(DrawableTransitionOptions.withCrossFade())
//            .into(holder.image)
//        holder.mView.setOnClickListener {
//            onItemClick.onItemClick(item.get(position).ads_block_image_id, null, "ads_block")
//        }
//    }


}

//        return if (item.size > 2) {
//            ViewHolder(
//                    LayoutInflater.from(context).inflate(R.layout.item_view_staggered, parent, false)
//            )
//        } else ViewHolder(
//                LayoutInflater.from(context).inflate(R.layout.item_view_staggered1, parent, false)
//        )

//override fun onCreateViewHolder(
//    parent: ViewGroup,
//    viewType: Int
//): StaggeredViewAdapter.ViewHolder {
//
//    return if (item.size == 1) {
//        ViewHolder(
//            LayoutInflater.from(context).inflate(R.layout.item_view_staggered_1, parent, false)
//        )
//    } else if (item.size == 2) {
//        ViewHolder(
//            LayoutInflater.from(context).inflate(R.layout.item_view_staggered_2, parent, false)
//        )
//    } else if (item.size == 3) {
//        ViewHolder(
//            LayoutInflater.from(context).inflate(R.layout.item_view_staggered_3, parent, false)
//        )
//    } else ViewHolder(
//        LayoutInflater.from(context).inflate(R.layout.item_view_staggered_4, parent, false)
//    )
//}


