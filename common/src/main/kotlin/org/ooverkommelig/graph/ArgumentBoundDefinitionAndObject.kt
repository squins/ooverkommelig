package org.ooverkommelig.graph

import org.ooverkommelig.definition.ObjectCreatingDefinition

internal class ArgumentBoundDefinitionAndObject<TObject>(definition: ObjectCreatingDefinition<TObject>, argument: Any?, private val obj: TObject) :
        DefinitionAndArgument<TObject>(definition, argument) {
    fun wire() {
        definition.wire(obj)
    }

    fun init() {
        definition.init(obj)
    }

    fun dispose() {
        definition.dispose(obj)
    }
}
