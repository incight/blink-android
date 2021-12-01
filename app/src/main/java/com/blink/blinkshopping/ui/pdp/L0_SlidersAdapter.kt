package com.blink.blinkshopping.ui.pdp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.blink.blinkshopping.R
import com.blink.blinkshopping.models.BaseSlider
import com.blink.blinkshopping.util.Url
import com.bumptech.glide.Glide
import org.jsoup.Jsoup

class L0_SlidersAdapter(
    var bannerIds: BaseSlider,
    var onItemClick: SliderItemClickHandler,
    var from: String
) : PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val viewItem: View = inflater.inflate(R.layout.item_l0_slider_custom, container, false)
        val imageView = viewItem.findViewById<ImageView>(R.id.imageView)

        var path: String = ""
        path = Url
        if (bannerIds.data!!.sliderById.get(0).items.get(position).image.contains("https:")) {
            Glide.with(container.context)
                .load(bannerIds.data!!.sliderById.get(0).items.get(position).image).into(imageView)
        } else {
            Glide.with(container.context)
                .load(path + bannerIds.data!!.sliderById.get(0).items.get(position).image)
                .into(imageView)
        }

        viewItem.setOnClickListener {
            onItemClick.onItemClick(
                bannerIds.data!!.sliderById.get(0).items.get(position).banner_id.toString(),
                "L0_Sliders"
            )
        }
        container.addView(viewItem)

        return viewItem
    }

    fun html2text(html: String?): String? {
        return Jsoup.parse(html).text()
    }


    override fun getCount(): Int {
        return bannerIds.data!!.sliderById.get(0).items.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    interface SliderItemClickHandler {
        fun onItemClick(banner_id: String, from: String)
    }


}
