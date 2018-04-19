package com.kevin.navmedia

import android.app.Application
import com.kevin.navmedia.di.AppComponent

/**
 * Created by quf93 on 2018-04-19.
 */
class App : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}