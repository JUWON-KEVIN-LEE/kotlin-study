package com.kevin.navmedia.di

import android.app.Application
import com.kevin.navmedia.NMediaApplication
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Created by quf93 on 2018-05-06.
 */

@Component(modules = arrayOf(

))
interface AppComponent {
    fun inject(application: NMediaApplication)
}

@Module
class AppModule(private val application: Application) {
    @Provides fun application() = application
}



