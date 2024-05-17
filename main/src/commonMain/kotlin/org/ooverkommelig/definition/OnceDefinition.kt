package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.DefinitionCreationContext
import org.ooverkommelig.SubGraphDefinition

internal class OnceDefinition<TObject>(
    override val owner: SubGraphDefinition,
    override val name: String,
    override val delegate: OnceDelegate<TObject>,
    private val context: DefinitionCreationContext
) :
    Definition<TObject>(),
    ObjectCreatingDefinition<TObject> {

    private val valueCreator = OnceCreator(this, null) { delegate.create(context) }

    override fun get() = valueCreator.getOrCreate()

    override fun toString() = "Once definition $name"
}
