package org.ooverkommelig.examples.ooverkommelig.cycles

import org.ooverkommelig.Definition
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class CyclesSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase

    val mainMenu: Definition<MainMenu> by Singleton { MainMenu() }
            .wire {
                it.eatStep = req(eatMenu)
                it.drinkStep = req(drinkMenu)
            }

    val eatMenu by Singleton { EatMenu() }
            .wire { it.quitStep = req(mainMenu) }

    val drinkMenu by Singleton { DrinkMenu() }
            .wire { it.quitStep = req(mainMenu) }
}
