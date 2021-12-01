package com.blink.blinkshopping.ui.pdp

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.blink.blinkshopping.R
import com.blink.blinkshopping.util.YOUTUBE_API_KEY
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment

class YoutubeViewPagerFragment(private val videoPath: String) : Fragment() {
    var mContext: FragmentActivity? = null
    private var YPlayer: YouTubePlayer? = null
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (activity is FragmentActivity) {
            mContext = activity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_youtube, container, false)
        val youTubePlayerFragment =
            YouTubePlayerSupportFragment.newInstance()
        val transaction =
            childFragmentManager.beginTransaction()
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit()
        youTubePlayerFragment.initialize(
            YOUTUBE_API_KEY,
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer,
                    b: Boolean
                ) {
                    if (!b) {
                        YPlayer = youTubePlayer
                        YPlayer!!.setFullscreen(false)
                        /*      YPlayer.loadVideo("2zNSgSzhBfM"); */
                        YPlayer!!.cueVideo(videoPath) //"2zNSgSzhBfM")
                        /*YPlayer.play();*/
                    }
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            })
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        mContext = activity
    }
}