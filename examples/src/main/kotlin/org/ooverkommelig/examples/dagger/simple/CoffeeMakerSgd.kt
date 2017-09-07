package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.D
import org.ooverkommelig.Once
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class CoffeeMakerSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase {
        fun heater(): D<Heater>
        fun pump(): D<Pump>
    }

    val coffeeMaker by Once { CoffeeMaker(lazy { req(provided.heater()) }, req(provided.pump())) }
}
