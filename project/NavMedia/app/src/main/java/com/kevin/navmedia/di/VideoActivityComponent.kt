package com.kevin.navmedia.di

import com.kevin.navmedia.ui.video.VideoActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by quf93 on 2018-04-19.
 */
@Singleton
@Component(
        modules = [ExoPlayerModule::class],
        dependencies = [AppComponent::class]
        )
interface VideoActivityComponent {
    fun inject(activity: VideoActivity): VideoActivity
}