package com.blink.blinkshopping.ui.pdp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import com.blink.blinkshopping.R
import com.blink.blinkshopping.databinding.FragmentExoPlayerBinding
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.LoopingMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.CacheDataSource
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import com.google.android.exoplayer2.util.Util
import com.nikhil.viewpager2withexoplayer2.application.ExoPlayerCaching
import kotlinx.android.synthetic.main.fragment_exo_player.*

class ExoPlayerFragment(
    private val videoPath: String,
    position: Int,
    private val from: String
) : Fragment(R.layout.fragment_exo_player) {


    private val TAG = "ExoPlayerFragment"

    private lateinit var simpleExoPlayer: SimpleExoPlayer

    private lateinit var mediaDataSourceFactory: DataSource.Factory

    private val positionOfFragment = position

    private lateinit var simpleCache: SimpleCache

    private var binding: FragmentExoPlayerBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExoPlayerBinding.bind(view)

        //println("in ExoPlayerFragment " + videoPath)
        Log.d(TAG, "onViewCreated: Fragment Position : $positionOfFragment")
        initializePlayer()
        initTouchEvent()
    }

    private fun initTouchEvent() {
        binding!!.flMainLayout.setOnClickListener {
            binding!!.playerView.player!!.playWhenReady =
                !binding!!.playerView.player!!.isPlaying

            if (binding!!.playerView.player!!.isPlaying) {
                binding!!.ivPauseVideo.visibility = GONE
            } else {
                binding!!.ivPauseVideo.visibility = VISIBLE
            }
        }
    }

    private fun initializePlayer() {

        simpleCache = ExoPlayerCaching.simpleCache!!

        simpleExoPlayer = SimpleExoPlayer.Builder(activity!!).build()
        val userAgent = Util.getUserAgent(activity!!, activity!!.getPackageName())
        mediaDataSourceFactory =
            DefaultDataSourceFactory(
                this.context,
                Util.getUserAgent(activity!!, userAgent)
            )
        val mediaSource = buildMediaSource(
            Uri.parse(videoPath),
            mediaDataSourceFactory as DefaultDataSourceFactory
        )

        val loopingSource = LoopingMediaSource(mediaSource)

        // Prepare the player with the source.
        simpleExoPlayer.prepare(loopingSource)
        // Autoplay the video when the player is ready
        // simpleExoPlayer.setPlayWhenReady(false);

        binding!!.playerView.useController = false  //For controller
        binding!!.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM)
        binding!!.playerView.setRepeatToggleModes(Player.REPEAT_MODE_ALL)
        binding!!.playerView.player = simpleExoPlayer


    }

    private fun buildMediaSource(
        uri: Uri,
        mediaDataSourceFactory: DefaultDataSourceFactory
    ): MediaSource {

        //adding caching
        val cacheDataSourceFactory = CacheDataSourceFactory(
            simpleCache, mediaDataSourceFactory, CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR
        )

        // This is the MediaSource representing the media to be played.
        val extension: String = uri.toString().substring(uri.toString().lastIndexOf("."))
        if (extension.contains("mp4")) {
            return ProgressiveMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(uri)
        } else {
            return HlsMediaSource.Factory(cacheDataSourceFactory)
                .createMediaSource(uri)
        }
    }

    override fun onResume() {
        super.onResume()
       // println("onResume")
        if (binding != null) {

            if (from.equals("HomeFrag_Video")) {
             //   println(" onResume .isPlaying " + !binding!!.playerView.player!!.isPlaying)
                binding!!.playerView.player!!.playWhenReady = true
                binding!!.ivPauseVideo.visibility = GONE
            } else {
            //    println(" onResume .isPlaying " + !binding!!.playerView.player!!.isPlaying)
                binding!!.playerView.player!!.playWhenReady = false
                binding!!.ivPauseVideo.visibility = VISIBLE
            }

        }
    }

    override fun onPause() {
        super.onPause()
      //  println("onPause")
        if (binding != null) {
     //       println(" onPause .isPlaying " + !binding!!.playerView.player!!.isPlaying)
            binding!!.playerView.player!!.playWhenReady = false
            binding!!.ivPauseVideo.visibility = VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    //    println("onDestroyView")
        binding = null
        simpleExoPlayer.release()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
     //   println("onAttach")
    }

    override fun onStart() {
        super.onStart()
      //  println("onStart")
    }

    override fun onDetach() {
        super.onDetach()
       // println("onDetach")
    }

    override fun onStop() {
        super.onStop()
     //   println("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
     //  println("onDestroy")
    }


}