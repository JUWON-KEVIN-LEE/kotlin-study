package com.kevin.navmedia.di

import android.app.Application
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by quf93 on 2018-04-19.
 */
@Module
class ExoPlayerModule(private val uri: Uri) {

    private val applicationName: String = "NavMedia"

    @Singleton @Provides
    fun provideMediaSource(factory:DefaultDataSourceFactory) : MediaSource {
        @C.ContentType val type = Util.inferContentType(uri)

        return when(type) {
            C.TYPE_SS -> SsMediaSource
                    .Factory(DefaultSsChunkSource.Factory(factory), factory)
                    .createMediaSource(uri)
            C.TYPE_DASH -> DashMediaSource
                    .Factory(DefaultDashChunkSource.Factory(factory),factory)
                    .createMediaSource(uri)
            C.TYPE_HLS -> HlsMediaSource
                    .Factory(factory)
                    .createMediaSource(uri)
            C.TYPE_OTHER -> ExtractorMediaSource
                    .Factory(factory)
                    .createMediaSource(uri)
            else -> throw IllegalStateException("Unsupprted Type : " + type)
        }
    }

    @Singleton @Provides
    fun provideDataSourceFactory(app: Application, bandwidthMeter: BandwidthMeter) : DefaultDataSourceFactory {
        return DefaultDataSourceFactory(
                app,
                Util.getUserAgent(app, applicationName),
                bandwidthMeter as TransferListener<in DataSource>)
    }

    @Singleton @Provides
    fun provideBandWidthMeter(): BandwidthMeter = DefaultBandwidthMeter()
}