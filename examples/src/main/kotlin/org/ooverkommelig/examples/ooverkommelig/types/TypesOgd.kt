package org.ooverkommelig.examples.ooverkommelig.types

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.req

class TypesOgd(provided: Provided) : ObjectGraphDefinition(provided) {
    interface Provided : TypesSgd.Provided

    inner class Graph : DefinitionObjectGraph() {
        fun mainRunnable() = req(typesSgd.mainRunnable)
    }

    val typesSgd = add(TypesSgd(provided))
}
