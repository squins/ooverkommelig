package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.D
import org.ooverkommelig.Once
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class PhoneSgd(provided: Provided) : SubGraphDefinition() {
    interface Provided {
        fun contacts(): D<Contacts>
    }

    val phone by Once { Phone(req(provided.contacts())) }
}
