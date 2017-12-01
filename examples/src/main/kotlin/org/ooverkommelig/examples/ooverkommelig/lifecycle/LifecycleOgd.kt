package org.ooverkommelig.examples.ooverkommelig.lifecycle

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.req

class LifecycleOgd(provided: Provided) : ObjectGraphDefinition(provided) {
    interface Provided : LifecycleSgd.Provided

    inner class Graph : DefinitionObjectGraph() {
        fun service() = req(lifecycleSgd.service)
    }

    val lifecycleSgd = add(LifecycleSgd(provided))
}
