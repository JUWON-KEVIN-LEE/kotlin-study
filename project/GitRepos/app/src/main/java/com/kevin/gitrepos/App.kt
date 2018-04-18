package com.kevin.gitrepos

import android.app.Application
import com.kevin.gitrepos.di.AppComponent
import com.kevin.gitrepos.di.DaggerAppComponent

/**
 * Created by quf93 on 2018-04-17.
 */
class App:Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()

        component.inject(this)
    }
}