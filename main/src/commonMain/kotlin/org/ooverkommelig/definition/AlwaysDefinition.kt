package org.ooverkommelig.definition

import org.ooverkommelig.Always
import org.ooverkommelig.Definition
import org.ooverkommelig.DefinitionCreationContext
import org.ooverkommelig.SubGraphDefinition

internal class AlwaysDefinition<TObject>(
    override val owner: SubGraphDefinition,
    override val name: String,
    override val delegate: Always<TObject>,
    private val context: DefinitionCreationContext
) :
    Definition<TObject>(),
    ObjectCreatingDefinition<TObject> {

    override fun get() = handleCreation { delegate.create(context) }

    override fun toString() = "Always definition $name"
}
