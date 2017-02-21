package org.ooverkommelig.examples.ooverkommelig.lifecycle

import java.util.concurrent.CountDownLatch

class BackgroundTask : Runnable {
    @Volatile
    private var mustStop = false
    private val hasStoppedLatch = CountDownLatch(1)

    override fun run() {
        try {
            while (!mustStop) {
                println("Doing background tasks: ${System.currentTimeMillis()}")
                Thread.sleep(500)
            }
        } finally {
            hasStoppedLatch.countDown()
        }
    }

    fun stop() {
        mustStop = true
        hasStoppedLatch.await()
    }
}
