package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.D
import org.ooverkommelig.Once
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class PumpSgd(provided: Provided) : SubGraphDefinition() {
    interface Provided {
        fun heater(): D<Heater>
    }

    val pump by Once { Thermosiphon(req(provided.heater())) }
}
