package com.kevin.navmedia.ui.video

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import com.kevin.navmedia.R
import kotlinx.android.synthetic.main.activity_video.*
import javax.inject.Inject

class VideoActivity : AppCompatActivity() {

    private lateinit var player: SimpleExoPlayer
    private var autoPlay: Boolean = true
    private lateinit var trackSelector: DefaultTrackSelector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
    }

    private fun initPlayer(uriString: String, bandwidthMeter: BandwidthMeter) {
        try {
            val mediaSource = PlayerUtil.buildMediaSource(this,
                    Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))

            val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
            playerView.requestFocus()
            trackSelector = DefaultTrackSelector(trackSelectionFactory)

            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

            playerView.player = player
            player.playWhenReady = autoPlay
            player.prepare(mediaSource)
        } catch (exception: IllegalArgumentException) {
            Log.d(TAG, exception.toString())
        }
    }

    @Inject
    fun setMediaSource(mediaSource: MediaSource) : MediaSource = mediaSource

    private fun initPlayer() {
        val bandwidthMeter = DefaultBandwidthMeter()
        val extractorsFactory = DefaultExtractorsFactory()
        val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)

        val mediaSourceFactory = DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "NavMedia"),
                bandwidthMeter as TransferListener<in DataSource>)

        val mediaSource = ExtractorMediaSource
                .Factory(mediaSourceFactory)
                .setExtractorsFactory(extractorsFactory)
                .createMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))

        playerView.requestFocus()
        trackSelector = DefaultTrackSelector(trackSelectionFactory)

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        playerView.player = player
        player.playWhenReady = autoPlay
        player.prepare(mediaSource)
    }

    private fun releasePlayer() {
        player.release()
        autoPlay = player.playWhenReady
    }

    override fun onStart() {
        super.onStart()
        if(Util.SDK_INT > 23) initPlayer()
    }

    override fun onResume() {
        super.onResume()
        if(Util.SDK_INT <= 23) initPlayer()
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
        const val TAG = "JUWONLEE"
    }
}
