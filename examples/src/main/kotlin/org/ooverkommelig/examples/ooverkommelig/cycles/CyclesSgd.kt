package org.ooverkommelig.examples.ooverkommelig.cycles

import org.ooverkommelig.D
import org.ooverkommelig.Once
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

@Suppress("MemberVisibilityCanBePrivate")
class CyclesSgd : SubGraphDefinition() {
    val mainMenu: D<MainMenu> by Once { MainMenu() }
        .wire {
            it.eatStep = req(eatMenu)
            it.drinkStep = req(drinkMenu)
        }

    val eatMenu by Once { EatMenu() }
        .wire { it.quitStep = req(mainMenu) }

    val drinkMenu by Once { DrinkMenu() }
        .wire { it.quitStep = req(mainMenu) }
}
