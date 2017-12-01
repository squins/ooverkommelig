package org.ooverkommelig.definition

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
            override val objectGraphDefinition by lazy { throw UnsupportedOperationException() }
        }

        val name = owner.name

        assertEquals("?", name)
    }
}

private class ClassOwner : SubGraphDefinitionOwner() {
    override val objectGraphDefinition by lazy { throw UnsupportedOperationException() }
}

private object NamedObjectOwner : SubGraphDefinitionOwner() {
    override val objectGraphDefinition by lazy { throw UnsupportedOperationException() }
}
