package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.owner
import org.ooverkommelig.req

class PhoneFlashMemoryContactsOgd(provided: Provided) : ObjectGraphDefinition(provided) {
    interface Provided : PhoneSgd.Provided, FlashMemorySgd.Provided {
        fun flashMemorySgd() = owner<PhoneFlashMemoryContactsOgd>().flashMemorySgd
        override fun contacts() = flashMemorySgd().flashMemory
    }

    inner class Graph : DefinitionObjectGraph() {
        fun phone() = req(phoneSgd.phone)
    }

    val phoneSgd = add(PhoneSgd(provided))
    val flashMemorySgd = add(FlashMemorySgd(provided))
}
