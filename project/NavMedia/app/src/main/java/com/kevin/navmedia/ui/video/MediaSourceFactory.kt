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
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener
import com.google.android.exoplayer2.util.Util

/**
 * Created by quf93 on 2018-04-20.
 */
class MediaSourceFactory {
    companion object {
        /**
         * Return DefaultDataSourceFactory
         */
        private fun buildDataSourceFactory(context: Context, useBandwidthMeter: Boolean) : DataSource.Factory {
            return buildDataSourceFactory(context,
                    if(useBandwidthMeter) DefaultBandwidthMeter() else null)
        }

        private fun buildDataSourceFactory(context: Context, bandwidthMeter: DefaultBandwidthMeter?) : DataSource.Factory {
            return DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(context, "NavMedia"),
                    bandwidthMeter as TransferListener<in DataSource>)
        }

        /**
         * Return MediaSource using streaming or other media type (C.ContentType)
         */
        fun buildMediaSource(context: Context, uri: Uri) : MediaSource {
            @C.ContentType val type = Util.inferContentType(uri)
            val dataSourceFactory : DataSource.Factory = buildDataSourceFactory(context, false)

            return when(type) {
                C.TYPE_SS -> SsMediaSource
                        .Factory(DefaultSsChunkSource.Factory(dataSourceFactory), dataSourceFactory)
                        .createMediaSource(uri)
                C.TYPE_DASH -> DashMediaSource
                        .Factory(DefaultDashChunkSource.Factory(dataSourceFactory),dataSourceFactory)
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
    }
}