package com.kevin.gitrepos.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * Created by quf93 on 2018-04-17.
 */
class AppExecutors(val diskIO: Executor = Executors.newSingleThreadExecutor(),
                   val networkIO: Executor = Executors.newFixedThreadPool(3),
                   val mainThread: Executor) {

    class MainThreadExecutor : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())

        override fun execute(command: Runnable?) {
            mainThreadHandler.post(command)
        }
    }
}