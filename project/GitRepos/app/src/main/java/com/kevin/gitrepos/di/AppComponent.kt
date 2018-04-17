package com.kevin.gitrepos.di

import android.app.Application
import com.kevin.gitrepos.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by quf93 on 2018-04-17.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: Application)
    fun inject(main: MainActivity)
}