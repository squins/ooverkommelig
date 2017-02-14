package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.Definition
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class CoffeeMakerSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase {
        fun heater(): Definition<Heater>
        fun pump(): Definition<Pump>
    }

    val coffeeMaker by Singleton { CoffeeMaker(lazy { req(provided.heater()) }, req(provided.pump())) }
}
