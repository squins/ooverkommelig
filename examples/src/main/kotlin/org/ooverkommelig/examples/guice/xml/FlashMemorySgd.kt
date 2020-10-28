package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.Once
import org.ooverkommelig.SubGraphDefinition

class FlashMemorySgd : SubGraphDefinition() {
    val flashMemory by Once { FlashMemory() }
}
