package org.ooverkommelig.graph

import org.ooverkommelig.definition.ObjectCreatingDefinition

internal open class DefinitionAndArgument<TObject>(
    val definition: ObjectCreatingDefinition<TObject>,
    val argument: Any?
) {
    fun fullyQualifiedName() = definition.name + (argument?.let { "($argument)" } ?: "")
}
