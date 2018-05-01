package com.kevin.navmedia.ui.video

import android.content.Context
import android.content.res.Configuration
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.View
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import com.kevin.navmedia.App
import com.kevin.navmedia.R
import com.kevin.navmedia.view.exoplayer.GestureManager
import com.kevin.navmedia.view.exoplayer.NPlaybackControlView.OnOrientationChangedListener.LANDSCAPE
import com.kevin.navmedia.view.exoplayer.NPlaybackControlView.OnOrientationChangedListener.PORTRAIT
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {

    private val mediaSourceFactory: MediaSourceFactory = MediaSourceFactory(App.context())

    private val uriString: String = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"

    private lateinit var player: SimpleExoPlayer

    private var autoPlay: Boolean = true
    private var mResumeWindow: Int = 0
    private var mResumePosition: Long = 0

    private lateinit var gestureManager: GestureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        initView()
        initGestureManager()
    }

    private fun initGestureManager() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val screen = DisplayMetrics()
        gestureManager = GestureManager(this, audioManager, screen)

        playerView.setGestureManager(gestureManager)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        // TODO
        if(newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT) {

        } else if(newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }
    }

    private fun initView() {
        playerView.isPortrait = resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
        playerView.setOnOrientationChangedListener {
            when(it) {
                LANDSCAPE -> landscape()
                PORTRAIT -> portrait()
            }
        }
    }

    private fun portrait() {
        // window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        playerView.changeSystemUIPortrait()
        others.visibility = View.VISIBLE
    }

    private fun landscape() {
        // window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        playerView.changeSystemUILandscape()
        others.visibility = View.GONE
    }

    private fun initPlayer(uriString: String) {
        try
        {
            val uri = Uri.parse(uriString)
            val type = Util.inferContentType(uri)
            val mediaSource = mediaSourceFactory.buildMediaSource(uri, type)

            player = ExoPlayerFactory.newSimpleInstance(this, mediaSourceFactory.getTrackSelector())

            playerView.requestFocus()

            playerView.player = player
            player.playWhenReady = autoPlay
            player.prepare(mediaSource)

        } catch (e: Exception) {
            // TODO Error Handling
        }
    }

    private fun releasePlayer() {
        player.release()
        autoPlay = player.playWhenReady
    }

    override fun onStart() {
        super.onStart()
        if(Util.SDK_INT > 23) initPlayer(uriString)
    }

    override fun onResume() {
        super.onResume()
        if(Util.SDK_INT <= 23) initPlayer(uriString)
    }

    override fun onPause() {
        super.onPause()
        if(Util.SDK_INT <= 23) releasePlayer()
    }

    override fun onStop() {
        super.onStop()
        if(Util.SDK_INT > 23) releasePlayer()
    }

    companion object {
        const val STATE_RESUME_WINDOW = "resumeWindow"
        const val STATE_RESUME_POSITION = "resumePosition"
        const val STATE_FULLSCREEN = "fullScreen"
    }
}
