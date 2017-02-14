package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.Definition
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class PumpSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase {
        fun heater(): Definition<Heater>
    }

    val pump by Singleton { Thermosiphon(req(provided.heater())) }
}
