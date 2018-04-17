package com.kevin.gitrepos

import android.app.Application
import com.kevin.gitrepos.di.AppComponent
import com.kevin.gitrepos.di.AppModule
import com.kevin.gitrepos.di.DaggerAppComponent

/**
 * Created by quf93 on 2018-04-17.
 */
class App:Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}