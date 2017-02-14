package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.owner
import org.ooverkommelig.req

class PhoneSimCardContactsOgd(provided: Provided) : ObjectGraphDefinition(provided) {
    interface Provided : PhoneSgd.Provided, SimCardSgd.Provided {
        fun simCardSgd() = owner<PhoneSimCardContactsOgd>().simCardSgd
        override fun contacts() = simCardSgd().simCard
    }

    inner class Graph : DefinitionObjectGraph() {
        fun phone() = req(phoneSgd.phone)
    }

    val phoneSgd = add(PhoneSgd(provided))
    val simCardSgd = add(SimCardSgd(provided))
}
