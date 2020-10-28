package org.ooverkommelig.examples.ooverkommelig.lifecycle

fun main() {
    LifecycleOgd().Graph().use { graph ->
        // Request the service so it is created. Another possibility would be to eagerly load the service.
        graph.service()
        Thread.sleep(3000)
    }
}
