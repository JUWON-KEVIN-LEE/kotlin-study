package com.kevin.navmedia

import android.app.Application
import android.content.Context

/**
 * Created by quf93 on 2018-04-19.
 */
class NMediaApplication : Application() {

    /*
    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .build()
    }
    */


    init {
        instance = this
    }

    companion object {
        private lateinit var instance: NMediaApplication

        fun context() : Context = instance.applicationContext
    }


    override fun onCreate() {
        super.onCreate()

        val context: Context = NMediaApplication.context()
    }


}