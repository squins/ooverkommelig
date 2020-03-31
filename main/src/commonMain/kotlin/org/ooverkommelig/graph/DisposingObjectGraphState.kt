package org.ooverkommelig.graph

import org.ooverkommelig.definition.ObjectCreatingDefinition

internal class DisposingObjectGraphState : FollowingObjectGraphState {
    private lateinit var graph: ObjectGraphImpl

    override fun enter(graph: ObjectGraphImpl) {
        this.graph = graph

        cleanUp("dispose", ArgumentBoundDefinitionAndObject<*>::dispose, graph.objectsToBeInitialized)
        runCleanUpOfObjectLifecyclesThatWereSetUp(graph)

        graph.transition(DisposedObjectGraphState())
    }

    private fun runCleanUpOfObjectLifecyclesThatWereSetUp(graph: ObjectGraphImpl) {
        graph.objectlessLifecyclesOfWhichSetUpHasRun.reversed().forEach { lifecycle ->
            try {
                lifecycle.dispose()
            } catch (exception: Exception) {
                graph.logCleanUpError(lifecycle, "dispose", exception)
            }
        }
    }

    private fun cleanUp(operation: String,
                        cleanUpFunction: (ArgumentBoundDefinitionAndObject<*>) -> Unit,
                        objectsForWhichCorrespondingSetUpStepWasNotRun: Collection<ArgumentBoundDefinitionAndObject<*>>) {
        val objectsToCleanUp = graph.objectsInCreationOrder
        objectsToCleanUp.removeAll(objectsForWhichCorrespondingSetUpStepWasNotRun)
        objectsToCleanUp.reversed().forEach { objectToCleanUp: ArgumentBoundDefinitionAndObject<*> ->
            try {
                cleanUpFunction(objectToCleanUp)
            } catch (exception: Exception) {
                logCleanUpError(objectToCleanUp.fullyQualifiedName(), operation, exception)
            }
        }
    }

    override fun creationStarted(definition: ObjectCreatingDefinition<*>, argument: Any?) = throw UnsupportedOperationException("Cannot create objects while disposing.")

    override fun <TObject> creationEnded(definition: ObjectCreatingDefinition<TObject>, argument: Any?, createdObject: TObject?) = throw UnsupportedOperationException("Cannot create objects while disposing.")

    override fun creationFailed() = throw UnsupportedOperationException("Cannot create objects while disposing.")

    override fun logCleanUpError(sourceObject: Any, operation: String, exception: Exception) {
        graph.configuration.logger.errorDuringCleanUp(sourceObject, operation, exception)
    }

    override fun dispose() = throw UnsupportedOperationException("Cannot dispose a disposing object graph.")
}
