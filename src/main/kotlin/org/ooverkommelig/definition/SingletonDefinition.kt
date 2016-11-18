package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.Singleton
import org.ooverkommelig.SubGraphDefinition

internal class SingletonDefinition<TObject>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val delegate: Singleton<TObject>) :
        Definition<TObject>(),
        ObjectCreatingDefinition<TObject> {

    private val value by lazy { handleCreation { delegate.create() } }

    override fun get() = value

    override fun toString() = "Singleton definition $name"
}
