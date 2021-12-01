package com.blink.blinkshopping.ui.pdp

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PdpViewPagerAdapter(
        private val activity: Fragment,
        private val videoList: ArrayList<PdpImagesModel>,
        private val from: String
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return videoList.size
    }

    override fun createFragment(position: Int): Fragment {

        if (videoList[position].isVideo) {
            return ExoPlayerFragment(videoList[position].videoUrl, position,from )
        } else {
            return ImageViewPagerFragment(videoList[position].imgurl,from)
        }

//        if (videoList[position].media_type.equals("external-video")) {
//            return ExoPlayerFragment(videoList[position].videoUrl, position)
//        } else if (videoList[position].media_type.equals("Youtube")) {
//            return YoutubeViewPagerFragment(videoList[position].videoUrl)
//        } else {
//            return ImageViewPagerFragment(videoList[position].imgurl)
//        }

    }
}