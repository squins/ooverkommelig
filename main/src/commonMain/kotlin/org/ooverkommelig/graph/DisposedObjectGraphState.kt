package org.ooverkommelig.graph

import org.ooverkommelig.definition.ObjectCreatingDefinition

internal class DisposedObjectGraphState : FollowingObjectGraphState {
    override fun enter(graph: ObjectGraphImpl) {
        // No action needed. The owner does not need to be stored, because this state neither needs information from the
        // object graph, nor needs to transition to another state.
    }

    override fun creationStarted(definition: ObjectCreatingDefinition<*>, argument: Any?) =
        throw UnsupportedOperationException("Cannot create objects while disposed.")

    override fun <TObject> creationEnded(
        definition: ObjectCreatingDefinition<TObject>,
        argument: Any?,
        createdObject: TObject?
    ) = throw UnsupportedOperationException("Cannot create objects while disposed.")

    override fun creationFailed() {
        // No action needed. This can happen when requesting an object fails
        // in a lifecycle method other than the creation method.
    }

    override fun logCleanUpError(sourceObject: Any, operation: String, exception: Exception) =
        throw UnsupportedOperationException("Cannot clean up sub graphs and objects while disposed.")

    override fun dispose() {
        // No action needed. Allow disposing an object graph multiple times.
    }
}
