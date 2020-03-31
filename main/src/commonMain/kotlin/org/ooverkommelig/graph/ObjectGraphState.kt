package org.ooverkommelig.graph

import org.ooverkommelig.definition.ObjectCreatingDefinition

internal interface ObjectGraphState : ObjectGraphProtocol {
    fun creationStarted(definition: ObjectCreatingDefinition<*>, argument: Any?)

    fun <TObject> creationEnded(definition: ObjectCreatingDefinition<TObject>, argument: Any?, createdObject: TObject?)

    fun creationFailed()
}
