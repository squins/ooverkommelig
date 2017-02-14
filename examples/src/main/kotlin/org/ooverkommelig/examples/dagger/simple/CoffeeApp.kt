package org.ooverkommelig.examples.dagger.simple

import org.ooverkommelig.ProvidedAdministration

class CoffeeApp(private val coffeeMaker: CoffeeMaker) : Runnable {
    override fun run() {
        coffeeMaker.brew()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            CoffeeAppOgd(object : CoffeeAppOgd.Provided, ProvidedAdministration() {}).Graph().use { graph ->
                graph.coffeeApp().run()
            }
        }
    }
}
