package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition

class DripCoffeeSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase

    val heater by Singleton { ElectricHeater() }
}
