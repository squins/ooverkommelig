package org.ooverkommelig.definition

import org.ooverkommelig.ObjectGraphDefinition
import kotlin.test.Test
import kotlin.test.assertEquals

class SubGraphDefinitionOwnerTest {
    @Test
    fun nameEqualsFullyQualifiedClassName() {
        val owner = object : SubGraphDefinitionOwner() {
            override val objectGraphDefinition by lazy<ObjectGraphDefinition> { throw UnsupportedOperationException() }
        }

        val name = owner.name

        assertEquals(owner.javaClass.name, name)
    }
}
