package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.Definition
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class PhoneSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase {
        fun contacts(): Definition<Contacts>
    }

    val phone by Singleton { Phone(req(provided.contacts())) }
}
