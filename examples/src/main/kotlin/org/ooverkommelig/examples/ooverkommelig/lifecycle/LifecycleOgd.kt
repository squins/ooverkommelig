package org.ooverkommelig.examples.ooverkommelig.lifecycle

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.req

class LifecycleOgd : ObjectGraphDefinition() {
    inner class Graph : DefinitionObjectGraph() {
        fun service() = req(lifecycleSgd.service)
    }

    val lifecycleSgd = add(LifecycleSgd())
}
