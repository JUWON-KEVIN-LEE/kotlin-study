package com.kevin.navmedia.ui.video

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener
import com.google.android.exoplayer2.util.Util

/**
 * Created by quf93 on 2018-04-20.
 */
class MediaSourceFactory constructor(private val context: Context) {

    private val dataSourceFactory: DataSource.Factory
    private val trackSelector: DefaultTrackSelector

    init {
        dataSourceFactory = buildDataSourceFactory(true)

        val adaptiveTrackSelectionFactory = AdaptiveTrackSelection.Factory(BANDWIDTH_METER)
        trackSelector = DefaultTrackSelector(adaptiveTrackSelectionFactory)
    }

    fun getTrackSelector(): DefaultTrackSelector = trackSelector

    /**
     * Return DefaultDataSourceFactory
     */
    private fun buildDataSourceFactory(useBandwidthMeter: Boolean) : DataSource.Factory
            = buildDataSourceFactory(if(useBandwidthMeter) BANDWIDTH_METER else null)

    private fun buildDataSourceFactory(bandwidthMeter: DefaultBandwidthMeter?) : DataSource.Factory
        = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context, applicationName),
                bandwidthMeter as TransferListener<in DataSource>)
        /*
        = DefaultDataSourceFactory(
                context,
                listener,
                DefaultHttpDataSourceFactory(Util.getUserAgent(context, applicationName), listener)
                )
        */

    /**
     * Return MediaSource using streaming or other media type (C.ContentType)
     */
    fun buildMediaSource(uri: Uri, @C.ContentType type: Int) : MediaSource {
        return when(type) {
            C.TYPE_SS -> SsMediaSource
                    .Factory(DefaultSsChunkSource.Factory(dataSourceFactory),
                            buildDataSourceFactory(false))
                    .createMediaSource(uri)
            C.TYPE_DASH -> DashMediaSource
                    .Factory(DefaultDashChunkSource.Factory(dataSourceFactory),
                            buildDataSourceFactory(false))
                    .createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(uri)
            C.TYPE_OTHER -> ExtractorMediaSource
                    .Factory(dataSourceFactory)
                    .createMediaSource(uri)
            else -> throw IllegalStateException("Unsupprted Type : " + type)
        }
    }

    companion object {
        private const val applicationName: String = "NavMedia"
        private val BANDWIDTH_METER = DefaultBandwidthMeter()
    }
}