package org.ooverkommelig.examples.ooverkommelig.types

import org.ooverkommelig.ProvidedAdministration

fun main(arguments: Array<String>) {
    TypesOgd(object : TypesOgd.Provided, ProvidedAdministration() {}).Graph().use { graph ->
        graph.mainRunnable().run()
    }
}
