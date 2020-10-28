package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.Once
import org.ooverkommelig.SubGraphDefinition

class DripCoffeeSgd : SubGraphDefinition() {
    val heater by Once { ElectricHeater() }
}
