package com.kevin.navmedia.ui.video

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.TransferListener
import com.google.android.exoplayer2.util.Util

/**
 * 코틀린에는 정적(Static) 변수 혹은 메소드가 없고,대신 패키지 내에 함수를 선언하여 사용할 수 있습니다.
 * 또는 companion object 를 사용할 수도 있습니다.
 * Created by quf93 on 2018-04-16.
 */
class PlayerUtil {
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

        /**
         * 풀 스크린
         */
        fun fullScreen(playerView: PlayerView, fullScreen: Dialog): Boolean {
            (playerView.parent as ViewGroup).removeView(playerView)
            fullScreen.addContentView(playerView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            fullScreen.show()

            return true
        }

        /**
         * 원래 사이즈의 스크린
         */
        fun closeFullScreen(playerView: PlayerView, fullScreen: Dialog, parent: ViewGroup): Boolean{
            (playerView.parent as ViewGroup).removeView(playerView)
            parent.addView(playerView)
            fullScreen.dismiss()

            return false
        }
    }
}