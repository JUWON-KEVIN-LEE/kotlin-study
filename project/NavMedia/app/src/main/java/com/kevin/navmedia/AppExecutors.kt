package com.kevin.navmedia

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by quf93 on 2018-04-21.
 */
class AppExecutors(val diskIO: Executor = Executors.newSingleThreadExecutor(),
                   val networkIO: Executor = Executors.newFixedThreadPool(N_THREADS),
                   val mainThread: Executor = MainThreadExecutor()) {

    class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }

    companion object {
        const val N_THREADS = 3
    }
}