package org.ooverkommelig.examples.guice.xml

class SimCard : Contacts {
    override fun findByName(name: String): Iterable<Contact> {
        return listOf(Contact("From SIM: $name"))
    }
}
