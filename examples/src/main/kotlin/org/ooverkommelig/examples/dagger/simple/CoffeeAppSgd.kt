package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.D
import org.ooverkommelig.Once
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class CoffeeAppSgd(provided: Provided) : SubGraphDefinition() {
    interface Provided {
        fun coffeeMaker(): D<CoffeeMaker>
    }

    val coffeeApp by Once { CoffeeApp(req(provided.coffeeMaker())) }
}
