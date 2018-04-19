package com.kevin.navmedia.di

import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by quf93 on 2018-04-19.
 */
@Module
class ExoPlayerModule {
    @Singleton @Provides
    fun provideBandWidthMeter(): BandwidthMeter {
        return DefaultBandwidthMeter()
    }

    @Singleton @Provides
    fun privideOkHttpClient() : BandwidthMeter {

    }
}