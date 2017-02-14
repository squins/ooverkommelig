package org.ooverkommelig.examples.guice.xml

interface Contacts {
    fun findByName(name: String): Iterable<Contact>
}
