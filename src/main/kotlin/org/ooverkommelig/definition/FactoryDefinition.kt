package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.Factory
import org.ooverkommelig.SubGraphDefinition

internal class FactoryDefinition<TObject>(
        override val owner: SubGraphDefinition,
        override val name: String,
        override val delegate: Factory<TObject>) :
        Definition<TObject>(),
        ObjectCreatingDefinition<TObject> {

    override fun get() = handleCreation { delegate.create() }

    override fun toString() = "Factory definition $name"
}
