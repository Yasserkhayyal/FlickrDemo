package com.android.khayal.flickrdemo

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject

open class AppExecutors(
    private val diskIO: Executor = Executors.newSingleThreadExecutor(),
    private val networkIO: Executor = Executors.newFixedThreadPool(3),
    private val mainThread: Executor = MainThreadExecutor()
) {

    fun diskIO(): Executor {
        return diskIO
    }

    fun networkIO(): Executor {
        return networkIO
    }

    fun mainThread(): Executor {
        return mainThread
    }

    class MainThreadExecutor @Inject constructor() : Executor {
        private val mainThreadHandler = Handler(Looper.getMainLooper())
        override fun execute(command: Runnable) {
            mainThreadHandler.post(command)
        }
    }
}