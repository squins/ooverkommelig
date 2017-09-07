package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.Once
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.SubGraphDefinition

class SimCardSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase

    val simCard by Once { SimCard() }
}
