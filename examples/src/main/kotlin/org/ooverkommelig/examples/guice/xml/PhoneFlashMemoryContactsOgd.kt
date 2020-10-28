package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.req

class PhoneFlashMemoryContactsOgd : ObjectGraphDefinition() {
    inner class Graph : DefinitionObjectGraph() {
        fun phone() = req(phoneSgd.phone)
    }

    val phoneSgd = add(PhoneSgd(object : PhoneSgd.Provided {
        override fun contacts() = flashMemorySgd.flashMemory
    }))
    val flashMemorySgd = add(FlashMemorySgd())
}
