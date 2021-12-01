package com.blink.blinkshopping.ui.allcategory

import android.R.attr.fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.LevelOneLayoutBinding
import com.blink.blinkshopping.models.allcategorymodel.ChildrenX


class L0_GridDetailsViewAdapter(
    private var context: Context,
    private var mutableList: MutableList<ChildrenX>,
    private var allCategoryViewModel: AllCategoryViewModel
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class MyViewHolder(levelOneLayoutBinding: LevelOneLayoutBinding) :
        RecyclerView.ViewHolder(levelOneLayoutBinding.root) {
        var binding: LevelOneLayoutBinding

        init {
            this.binding = levelOneLayoutBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LevelOneLayoutBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return mutableList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as MyViewHolder

        //var navController: NavController? = null

        holder.binding.tvHeading.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("productId", mutableList[position].id.toString())

//          Navigation.findNavController(holder.itemView).navigate(R.id.LTwoCatFragment, bundle)

            Navigation.findNavController(holder.itemView).navigate(R.id.action_allCategoryFragment_to_LTwoCatFragment, bundle)

            allCategoryViewModel.setL2ProPosition(position)

        }

        holder.binding.tvHeading.text = mutableList[position].name

        val chiledData = mutableList[position].children.toMutableList()
        if (chiledData != null && !chiledData.isEmpty()) {
            holder.binding.itemsLt.visibility = View.VISIBLE
        }

        val inseDataAdpater = InsideDataAdpater(context, chiledData)

        val layoutManager = GridLayoutManager(context, 3)

        var size = chiledData.size
        holder.binding.dataRecycler.layoutManager = layoutManager
//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                return if (position == size-1 && position and 1 == 0 ) 3 else 1
//            }
//        }
        holder.binding.dataRecycler.adapter = inseDataAdpater
        inseDataAdpater.notifyDataSetChanged()
    }

}


//val chidlData = mutableList[position]
// selectItemViewModel.setChildData(chidlData)
//selectItemViewModel.settoL2ChildPosition(position)


//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var recyclerView: AutofitRecyclerView = itemView.findViewById(R.id.dataRecycler)
//        var itemHeading: TextView = itemView.findViewById(R.id.tvHeading)
//        var itemsLt: CardView = itemView.findViewById(R.id.items_lt)
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val viewHolder =
//            LayoutInflater.from(parent.context).inflate(R.layout.level_one_layout, parent, false)
//        return MyViewHolder(viewHolder)
//    }


//        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//            override fun getSpanSize(position: Int): Int {
//                var modulo = position % 3
//                if (modulo == 0) {
//                    return 1
//                } else if (modulo == 1 || modulo == 2) {
//                    return 1
//                }  else {
//                    return 3
//                }
//            }
//        }

