package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.Once
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.SubGraphDefinition

class DripCoffeeSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase

    val heater by Once { ElectricHeater() }
}
