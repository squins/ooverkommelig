package org.ooverkommelig.graph

import org.ooverkommelig.definition.ObjectCreatingDefinition

internal class UninitializedObjectGraphState : FollowingObjectGraphState {
    private lateinit var graph: ObjectGraphImpl

    override fun enter(graph: ObjectGraphImpl) {
        this.graph = graph

        try {
            runSetUpOfObjectlessLifecycles()
            graph.transition(InitializedObjectGraphState())
        } catch (exception: Exception) {
            graph.transition(DisposingObjectGraphState())
            throw exception
        }
    }

    private fun runSetUpOfObjectlessLifecycles() {
        graph.objectlessLifecycles.forEach { lifecycle ->
            lifecycle.init()
            graph.objectlessLifecyclesOfWhichSetUpHasRun += lifecycle
        }
    }

    override fun creationStarted(definition: ObjectCreatingDefinition<*>, argument: Any?) =
        throw UnsupportedOperationException("Cannot create objects while uninitialized.")

    override fun <TObject> creationEnded(
        definition: ObjectCreatingDefinition<TObject>,
        argument: Any?,
        createdObject: TObject?
    ) = throw UnsupportedOperationException("Cannot create objects while uninitialized.")

    override fun creationFailed() = throw UnsupportedOperationException("Cannot create objects while uninitialized.")

    override fun logCleanUpError(sourceObject: Any, operation: String, exception: Exception) =
        throw UnsupportedOperationException("Cannot clean up sub graphs and objects while uninitialized.")

    override fun dispose() {
        graph.transition(DisposedObjectGraphState())
    }
}
