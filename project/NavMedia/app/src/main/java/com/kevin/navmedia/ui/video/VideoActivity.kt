package com.kevin.navmedia.ui.video

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util
import com.kevin.navmedia.App
import com.kevin.navmedia.R
import kotlinx.android.synthetic.main.activity_video.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*

class VideoActivity : AppCompatActivity() {

    private val mediaSourceFactory: MediaSourceFactory = MediaSourceFactory(App.context()/* should be refactored */)

    private val uriString: String = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"

    private lateinit var player: SimpleExoPlayer

    private var isLandscape: Boolean = false

    private var autoPlay: Boolean = true
    private var mResumeWindow: Int = 0
    private var mResumePosition: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        init()
    }

    private fun init() {
        screen.setOnClickListener {
            if(!isLandscape) {
                PlayerUtil.landscape(this, playerView, others)
                isLandscape = true
                others.visibility = View.GONE
            } else {
                PlayerUtil.portrait(this, playerView, others)
                isLandscape = false
                others.visibility = View.VISIBLE
            }
        }
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
