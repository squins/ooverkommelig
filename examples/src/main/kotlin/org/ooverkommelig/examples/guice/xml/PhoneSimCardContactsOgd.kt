package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.req

class PhoneSimCardContactsOgd : ObjectGraphDefinition() {
    inner class Graph : DefinitionObjectGraph() {
        fun phone() = req(phoneSgd.phone)
    }

    val phoneSgd = add(PhoneSgd(object : PhoneSgd.Provided {
        override fun contacts() = simCardSgd.simCard
    }))
    val simCardSgd = add(SimCardSgd())
}
