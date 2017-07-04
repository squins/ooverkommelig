package org.ooverkommelig.definition

import org.junit.Test
import kotlin.test.assertEquals

class SubGraphDefinitionOwnerTest {
    @Test
    fun nameEqualsFullyQualifiedClassName() {
        val owner = object : SubGraphDefinitionOwner() {
            override val objectGraphDefinition by lazy { throw UnsupportedOperationException() }
        }
        
        val name = owner.name
        
        assertEquals(owner.javaClass.name, name)
    }
}
