package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.Once
import org.ooverkommelig.SubGraphDefinition

class SimCardSgd : SubGraphDefinition() {
    val simCard by Once { SimCard() }
}
