package org.ooverkommelig.graph

import org.ooverkommelig.definition.ObjectCreatingDefinition

internal class CreatedObjectGraphState : ObjectGraphState {
    override fun creationStarted(definition: ObjectCreatingDefinition<*>, argument: Any?) = throw UnsupportedOperationException("Cannot create objects while just having been created.")

    override fun <TObject> creationEnded(definition: ObjectCreatingDefinition<TObject>, argument: Any?, createdObject: TObject?) = throw UnsupportedOperationException("Cannot create objects while just having been created.")

    override fun creationFailed() = throw UnsupportedOperationException("Cannot create objects while just having been created.")

    override fun logCleanUpError(sourceObject: Any, operation: String, exception: Exception) = throw UnsupportedOperationException("Cannot clean up sub graphs and objects while just having been created.")

    override fun dispose() = throw UnsupportedOperationException("Cannot dispose while just having been created.")
}
