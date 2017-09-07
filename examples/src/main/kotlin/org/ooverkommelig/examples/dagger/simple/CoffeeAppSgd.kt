package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.D
import org.ooverkommelig.Once
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class CoffeeAppSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase {
        fun coffeeMaker(): D<CoffeeMaker>
    }

    val coffeeApp by Once { CoffeeApp(req(provided.coffeeMaker())) }
}
