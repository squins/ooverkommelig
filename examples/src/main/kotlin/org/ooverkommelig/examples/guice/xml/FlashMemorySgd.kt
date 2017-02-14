package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition

class FlashMemorySgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase

    val flashMemory by Singleton { FlashMemory() }
}
