package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.D
import org.ooverkommelig.Once
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class PhoneSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase {
        fun contacts(): D<Contacts>
    }

    val phone by Once { Phone(req(provided.contacts())) }
}
