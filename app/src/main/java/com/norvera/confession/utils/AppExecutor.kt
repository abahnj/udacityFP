package com.norvera.confession.utils

import java.util.concurrent.Executor

class AppExecutor(
    // TODO Make class a singleton

    private val diskIO: Executor, private val mainThread: Executor
) {

    fun diskIO(): Executor {
        return diskIO
    }

    fun mainThread(): Executor {
        return mainThread
    }
}
