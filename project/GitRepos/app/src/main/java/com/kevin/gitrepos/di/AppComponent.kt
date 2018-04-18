package com.kevin.gitrepos.di

import android.app.Application
import com.kevin.gitrepos.ui.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by quf93 on 2018-04-17.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    // TODO DI 에 대해 다루기
    @Component.Builder
    interface Builder {
        @BindsInstance fun appModule(app: Application) : Builder
        fun build():AppComponent
    }

    fun inject(app: Application)
    fun inject(main: MainActivity)
}