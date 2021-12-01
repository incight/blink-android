package com.blink.blinkshopping.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.R
import com.blink.blinkshopping.customViews.CustomTextView
import com.blink.blinkshopping.util.Url
import com.bumptech.glide.Glide
import org.jsoup.Jsoup

class SlidersViewPagerAdapter(
    var bannerIds: ArrayList<Int?>,
    var images: ArrayList<String>,
    var titles: ArrayList<String>,
    var onItemClick: ItemClickHandler,
    var from: String
) : PagerAdapter() {


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(container.context)
        val viewItem: View = inflater.inflate(R.layout.item_slider_custom, container, false)
        val imageView = viewItem.findViewById<ImageView>(R.id.imageView)
        val txtOffer =
            viewItem.findViewById<CustomTextView>(R.id.txtOffer)

        if (titles.get(position) != null) {
            val strNew: String = html2text(titles.get(position).replace("Learn More", ""))!!
            txtOffer.setText(strNew)
        }

        if (from.equals("pdp")) {
            txtOffer.visibility = View.GONE
        }

        if (from.equals("LOne")) {
            txtOffer.visibility = View.GONE
        }

        var path: String = ""
        path = Url
        if (images.get(position).contains("https:")) {
            Glide.with(container.context).load(images.get(position)).into(imageView)
        } else {
            Glide.with(container.context).load(path + images.get(position)).into(imageView)
        }

        viewItem.setOnClickListener {
            if (bannerIds != null && bannerIds.isNotEmpty())
                onItemClick.onItemClick(bannerIds.get(position).toString(), null, "sliders")
            else
                onItemClick.onItemClick(titles.get(0), null, "sliders")
        }

        container.addView(viewItem)

        return viewItem
    }

    fun html2text(html: String?): String? {
        return Jsoup.parse(html).text()
    }


    override fun getCount(): Int {
        return images.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

}
