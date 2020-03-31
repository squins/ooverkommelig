package org.ooverkommelig.definition

import org.junit.Test
import org.ooverkommelig.ObjectGraphDefinition
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
