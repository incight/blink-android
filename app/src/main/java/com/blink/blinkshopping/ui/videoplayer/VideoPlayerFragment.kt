package com.blink.blinkshopping.ui.videoplayer

//import android.annotation.SuppressLint
//import android.content.Intent.getIntent
//import android.net.Uri
//import android.os.Bundle
//import android.os.Handler
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.ViewModelProviders
//import androidx.navigation.fragment.NavHostFragment
//import com.blink.blinkshopping.ItemClickHandler
//import com.blink.blinkshopping.R
//import com.blink.blinkshopping.databinding.ActivityVideoBinding
//import com.blink.blinkshopping.models.ProductItem
//import com.google.android.exoplayer2.*
//import com.google.android.exoplayer2.source.ExtractorMediaSource
//import com.google.android.exoplayer2.source.MediaSource
//import com.google.android.exoplayer2.source.TrackGroupArray
//import com.google.android.exoplayer2.trackselection.*
//import com.google.android.exoplayer2.upstream.*
//import com.google.android.exoplayer2.util.Util
//import dagger.android.DispatchingAndroidInjector
//import dagger.android.support.AndroidSupportInjection
//import dagger.android.support.HasSupportFragmentInjector
//import kotlinx.android.synthetic.main.activity_video.*
//import javax.inject.Inject
//
//
//class VideoPlayerFragment : Fragment(), HasSupportFragmentInjector,
//    Player.EventListener {
//
//    @Inject
//    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory
//
//    private lateinit var mainViewModel: VideoPlayeryFragmentViewModel
//    lateinit var binding: ActivityVideoBinding
//
//    override fun supportFragmentInjector() = dispatchingAndroidInjector
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(inflater, R.layout.activity_video, container, false)
//        return binding.root
//    }
//
//
//
//    @SuppressLint("SetTextI18n")
//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//        AndroidSupportInjection.inject(this)
//        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(VideoPlayeryFragmentViewModel::class.java)
//
//        if (arguments != null) {
//            videoUri = arguments!!.getString(KEY_VIDEO_URI)
//        }
//
//        close.setOnClickListener {
//            NavHostFragment.findNavController(parentFragment!!).popBackStack()
//        }
//
//        setUp()
//
//    }
//
//    var videoUri: String? = null
//    var player: SimpleExoPlayer? = null
//    var mHandler: Handler? = null
//    var mRunnable: Runnable? = null
//    var KEY_VIDEO_URI = "video"
//
//    private fun setUp() {
//        initializePlayer()
//        if (videoUri == null) {
//            return
//        }
//        buildMediaSource(Uri.parse(videoUri))//https://blinkdev.i95-dev.com/mgnupgrade/pub/media/video.mp4  for testing
//    }
//
//    private fun initializePlayer() {
//        if (player == null) {
//            // 1. Create a default TrackSelector
//            val loadControl: LoadControl = DefaultLoadControl(
//                DefaultAllocator(true, 16),
//                VideoPlayerConfig.MIN_BUFFER_DURATION,
//                VideoPlayerConfig.MAX_BUFFER_DURATION,
//                VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
//                VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true
//            )
//            val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()
//            val videoTrackSelectionFactory: TrackSelection.Factory =
//                AdaptiveTrackSelection.Factory(bandwidthMeter)
//            val trackSelector: TrackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
//            // 2. Create the player
//            player = ExoPlayerFactory.newSimpleInstance(
//                DefaultRenderersFactory(this@VideoPlayerFragment.context),
//                trackSelector,
//                loadControl
//            )
//            binding.videoFullScreenPlayer.player = player
//        }
//    }
//
//    private fun buildMediaSource(mUri: Uri) {
//        // Measures bandwidth during playback. Can be null if not required.
//        val bandwidthMeter = DefaultBandwidthMeter()
//        // Produces DataSource instances through which media data is loaded.
//        val dataSourceFactory: DataSource.Factory =
//            DefaultDataSourceFactory(
//                this@VideoPlayerFragment.context!!,
//                Util.getUserAgent(
//                    this@VideoPlayerFragment.context!!,
//                    getString(R.string.app_name)
//                ), bandwidthMeter
//            )
//        // This is the MediaSource representing the media to be played.
//        val videoSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
//            .createMediaSource(mUri)
//        // Prepare the player with the source.
//        player!!.prepare(videoSource)
//        player!!.setPlayWhenReady(true)
//        player!!.addListener(this)
//    }
//
//    private fun releasePlayer() {
//        if (player != null) {
//            player!!.release()
//            player = null
//        }
//    }
//
//    override fun onTimelineChanged(
//        timeline: Timeline?,
//        manifest: Any?,
//        reason: Int
//    ) {
//    }
//
//    override fun onTracksChanged(
//        trackGroups: TrackGroupArray?,
//        trackSelections: TrackSelectionArray?
//    ) {
//    }
//
//    override fun onLoadingChanged(isLoading: Boolean) {}
//
//    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
//        when (playbackState) {
//            Player.STATE_BUFFERING -> binding.spinnerVideoDetails.visibility = View.VISIBLE
//            Player.STATE_ENDED -> {
//            }
//            Player.STATE_IDLE -> {
//            }
//            Player.STATE_READY -> binding.spinnerVideoDetails.visibility = View.GONE
//            else -> {
//            }
//        }
//    }
//
//    override fun onRepeatModeChanged(repeatMode: Int) {}
//
//    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {}
//
//    override fun onPlayerError(error: ExoPlaybackException?) {}
//
//    override fun onPositionDiscontinuity(reason: Int) {}
//
//    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {}
//
//    override fun onSeekProcessed() {}
//
//
//
//    private fun pausePlayer() {
//        if (player != null) {
//            player!!.setPlayWhenReady(false)
//            player!!.getPlaybackState()
//        }
//    }
//
//    private fun resumePlayer() {
//        if (player != null) {
//            player!!.setPlayWhenReady(true)
//            player!!.getPlaybackState()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        pausePlayer()
//        if (mRunnable != null) {
//            mHandler!!.removeCallbacks(mRunnable)
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//    }
//
////    protected fun onRestart() {
////        super.onRestart()
////        resumePlayer()
////    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        releasePlayer()
//    }
//
//}
