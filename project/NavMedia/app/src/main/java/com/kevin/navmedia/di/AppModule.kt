package com.kevin.navmedia.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by quf93 on 2018-04-19.
 */
@Module
class AppModule(private val app: Application) {
    @Provides @Singleton
    fun provideApp(): Application = app
}