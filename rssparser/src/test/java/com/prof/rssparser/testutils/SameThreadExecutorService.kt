package com.prof.rssparser.testutils

import java.util.*
import java.util.concurrent.AbstractExecutorService
import java.util.concurrent.TimeUnit

/**
 * Executes all submitted tasks directly in the same thread as the caller.
 */
class SameThreadExecutorService : AbstractExecutorService() {
    @Volatile
    private var terminated = false
    override fun shutdown() {
        terminated = true
    }

    override fun isShutdown(): Boolean {
        return terminated
    }

    override fun isTerminated(): Boolean {
        return terminated
    }

    @Throws(InterruptedException::class)
    override fun awaitTermination(theTimeout: Long, theUnit: TimeUnit?): Boolean {
        shutdown()
        return terminated
    }

    override fun shutdownNow(): List<Runnable> {
        return Collections.emptyList()
    }

    override fun execute(theCommand: Runnable) {
        theCommand.run()
    }
}