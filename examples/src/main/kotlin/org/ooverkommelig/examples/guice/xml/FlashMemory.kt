package org.ooverkommelig.examples.guice.xml

class FlashMemory : Contacts {
    override fun findByName(name: String): Iterable<Contact> {
        return listOf(Contact("From flash: $name"))
    }
}
