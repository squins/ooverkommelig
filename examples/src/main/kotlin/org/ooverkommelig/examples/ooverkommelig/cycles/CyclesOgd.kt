package org.ooverkommelig.examples.ooverkommelig.cycles

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.req

class CyclesOgd(provided: Provided) : ObjectGraphDefinition(provided) {
    interface Provided : CyclesSgd.Provided

    inner class Graph : DefinitionObjectGraph() {
        fun mainMenu() = req(cyclesSgd.mainMenu)
    }

    val cyclesSgd = add(CyclesSgd(provided))
}
