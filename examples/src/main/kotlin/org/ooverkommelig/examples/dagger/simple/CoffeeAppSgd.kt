package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.Definition
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class CoffeeAppSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase {
        fun coffeeMaker(): Definition<CoffeeMaker>
    }

    val coffeeApp by Singleton { CoffeeApp(req(provided.coffeeMaker())) }
}
