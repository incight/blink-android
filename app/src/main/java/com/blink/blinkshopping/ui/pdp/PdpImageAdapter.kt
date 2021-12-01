package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.R
import com.blink.blinkshopping.util.Url
import com.bumptech.glide.Glide
import java.util.*

class PdpImageAdapter(
    private val models: ArrayList<PdpImagesModel>,
    private val context: Context,
    var onItemClick: ItemClickHandler
) : PagerAdapter() {

    override fun isViewFromObject(
        view: View,
        `object`: Any
    ): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mView = LayoutInflater.from(context).inflate(R.layout.item_pdp_image, container, false)
        val imageView = mView.findViewById<ImageView>(R.id.image)
        val ltExoplayer = mView.findViewById<ConstraintLayout>(R.id.lt_exoplayer)
//      val ivPlay: ImageView = mView.findViewById(R.id.iv_play)

        if (models[position].isVideo) {
            ltExoplayer.visibility = View.VISIBLE
            imageView.visibility = View.GONE
        } else {
            ltExoplayer.visibility = View.GONE
            imageView.visibility = View.VISIBLE
        }

        var path: String = ""
        path = Url
        if (models.get(position).imgurl.contains("https:")) {
            Glide.with(container.context).load(models.get(position).imgurl).into(imageView)
        } else {
            Glide.with(container.context).load(path + models.get(position).imgurl).into(imageView)
        }

//        mView.setOnClickListener {
//            if (models.get(position).id != null)
//                onItemClick.onItemClick(models.get(position).id.toString(), null, "sliders")
//            else
//                onItemClick.onItemClick(models.get(position).title, null, "sliders")
//        }

        container.addView(mView, 0)
        return mView
    }

    override fun getCount(): Int {
        return models.size
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        container.removeView(`object` as View)
    }

}