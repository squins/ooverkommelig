package org.ooverkommelig.examples.ooverkommelig.lifecycle

import org.ooverkommelig.ProvidedAdministration

fun main(arguments: Array<String>) {
    LifecycleOgd(object : LifecycleOgd.Provided, ProvidedAdministration() {}).Graph().use { graph ->
        // Request the service so it is created. Another possibility would be to eagerly load the service.
        graph.service()
        Thread.sleep(3000)
    }
}
