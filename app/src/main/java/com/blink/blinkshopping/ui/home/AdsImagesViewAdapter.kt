package com.blink.blinkshopping.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.databinding.*
import com.blink.blinkshopping.models.AdsItems

class AdsImagesViewAdapter(
    var context: Context,
    var item: MutableList<AdsItems>,
    var onItemClick: ItemClickHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class AdsImagesViewHolder1(itemviewAds1Binding: ItemviewAds1Binding) :
        RecyclerView.ViewHolder(itemviewAds1Binding.root) {
        var binding: ItemviewAds1Binding

        init {
            this.binding = itemviewAds1Binding
        }
    }

    class AdsImagesViewHolder2(itemViewStaggered2Binding: ItemViewStaggered2Binding) :
        RecyclerView.ViewHolder(itemViewStaggered2Binding.root) {
        var binding: ItemViewStaggered2Binding

        init {
            this.binding = itemViewStaggered2Binding
        }
    }

    class AdsImagesViewHolder3(itemViewStaggered3Binding: ItemViewStaggered3Binding) :
        RecyclerView.ViewHolder(itemViewStaggered3Binding.root) {
        var binding: ItemViewStaggered3Binding

        init {
            this.binding = itemViewStaggered3Binding
        }
    }

    class AdsImagesViewHolder4(itemViewStaggered4Binding: ItemViewStaggered4Binding) :
        RecyclerView.ViewHolder(itemViewStaggered4Binding.root) {
        var binding: ItemViewStaggered4Binding

        init {
            this.binding = itemViewStaggered4Binding
        }
    }

    class AdsImagesViewHolder5(itemViewStaggered5Binding: ItemViewStaggered5Binding) :
        RecyclerView.ViewHolder(itemViewStaggered5Binding.root) {
        var binding: ItemViewStaggered5Binding

        init {
            this.binding = itemViewStaggered5Binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       // if (item.size == 1) {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemviewAds1Binding.inflate(layoutInflater, parent, false)
            return AdsImagesViewHolder1(
                binding
            )
//        } else if (item.size == 2) {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ItemViewStaggered2Binding.inflate(layoutInflater, parent, false)
//            return AdsImagesViewHolder2(binding)
//        } else if (item.size == 3) {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ItemViewStaggered3Binding.inflate(layoutInflater, parent, false)
//            return AdsImagesViewHolder3(binding)
//        } else if (item.size == 4) {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ItemViewStaggered4Binding.inflate(layoutInflater, parent, false)
//            return AdsImagesViewHolder4(binding)
//        } else {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ItemViewStaggered5Binding.inflate(layoutInflater, parent, false)
//            return AdsImagesViewHolder5(binding)
//        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //   if (item.size == 1) {

//        var size = item.get(position).!!.size
//
//        viewHolder.binding.staggeredView = item.get(position)
//
//        var staggeredAdapter = StaggeredViewAdapter(context, adsBlocks.get(position).items,onItemClick)
//        holder.binding.rvAdsBlockInside.layoutManager = layoutManager
//

            val viewHolder: AdsImagesViewHolder1 = holder as AdsImagesViewHolder1
            viewHolder.binding.staggeredView = item.get(position)
            holder.binding.image.setOnClickListener {
                onItemClick.onItemClick(item.get(position).name, null, "ads_images")
            }
//        } else if (item.size == 2) {
//            val viewHolder: AdsImagesViewHolder2 = holder as AdsImagesViewHolder2
//            viewHolder.binding.staggeredView = item.get(position)
//            holder.binding.image.setOnClickListener {
//                onItemClick.onItemClick(item.get(position).name, null, "ads_images")
//            }
//        } else if (item.size == 3) {
//            val viewHolder: AdsImagesViewHolder3 = holder as AdsImagesViewHolder3
//            viewHolder.binding.staggeredView = item.get(position)
//            holder.binding.image.setOnClickListener {
//                onItemClick.onItemClick(item.get(position).name, null, "ads_images")
//            }
//        } else if (item.size == 4) {
//            val viewHolder: AdsImagesViewHolder4 = holder as AdsImagesViewHolder4
//            viewHolder.binding.staggeredView = item.get(position)
//            holder.binding.image.setOnClickListener {
//                onItemClick.onItemClick(item.get(position).name, null, "ads_images")
//            }
//        } else {
//            val viewHolder: AdsImagesViewHolder5 = holder as AdsImagesViewHolder5
//            viewHolder.binding.staggeredView = item.get(position)
//            holder.binding.image.setOnClickListener {
//                onItemClick.onItemClick(item.get(position).name, null, "ads_images")
//            }
//        }

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


