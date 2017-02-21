package org.ooverkommelig.examples.ooverkommelig.lifecycle

class ServiceWithBackgroundTask {
    private val backgroundTask = BackgroundTask()

    fun start() {
        Thread(backgroundTask).start()
    }

    fun stop() {
        backgroundTask.stop()
    }
}
