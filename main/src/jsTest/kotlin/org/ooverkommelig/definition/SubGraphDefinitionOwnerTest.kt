package org.ooverkommelig.definition

import org.ooverkommelig.ObjectGraphDefinition
import kotlin.test.Test
import kotlin.test.assertEquals

class SubGraphDefinitionOwnerTest {
    @Test
    fun nameEqualsSimpleClassNameForClassInstance() {
        val owner = ClassOwner()

        val name = owner.name

        assertEquals("ClassOwner", name)
    }

    @Test
    fun nameEqualsSimpleClassNameForNamedObject() {
        val owner = NamedObjectOwner

        val name = owner.name

        assertEquals("NamedObjectOwner", name)
    }

    @Test
    fun nameEqualsQuestionMarkForAnonymousObject() {
        val owner = object : SubGraphDefinitionOwner() {
            override val objectGraphDefinition by lazy<ObjectGraphDefinition> { throw UnsupportedOperationException() }
        }

        val name = owner.name

        assertEquals("?", name)
    }
}

private class ClassOwner : SubGraphDefinitionOwner() {
    override val objectGraphDefinition by lazy<ObjectGraphDefinition> { throw UnsupportedOperationException() }
}

private object NamedObjectOwner : SubGraphDefinitionOwner() {
    override val objectGraphDefinition by lazy<ObjectGraphDefinition> { throw UnsupportedOperationException() }
}
