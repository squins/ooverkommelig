package org.ooverkommelig.graph

import org.ooverkommelig.Definition
import org.ooverkommelig.ObjectGraph
import org.ooverkommelig.ObjectGraphConfiguration
import org.ooverkommelig.definition.ObjectCreatingDefinition
import org.ooverkommelig.definition.ObjectlessLifecycle

internal class ObjectGraphImpl(
    internal val configuration: ObjectGraphConfiguration,
    internal val objectlessLifecycles: List<ObjectlessLifecycle>,
    internal val objectsToCreateEagerly: List<Definition<*>>
) : ObjectGraph, ObjectGraphProtocol {
    internal val objectlessLifecyclesOfWhichSetUpHasRun = mutableListOf<ObjectlessLifecycle>()

    internal val objectsToBeInitialized = mutableListOf<ArgumentBoundDefinitionAndObject<*>>()

    internal val objectsInCreationOrder = mutableListOf<ArgumentBoundDefinitionAndObject<*>>()

    private var state: ObjectGraphState = CreatedObjectGraphState()

    internal fun init() {
        transition(UninitializedObjectGraphState())
    }

    internal fun transition(newState: FollowingObjectGraphState) {
        state = newState
        newState.enter(this)
    }

    internal fun <TObject> handleCreation(
        definition: ObjectCreatingDefinition<TObject>,
        argument: Any?,
        creator: () -> TObject
    ): TObject {
        val result: TObject?

        state.creationStarted(definition, argument)
        try {
            result = creator()
        } catch (exception: Exception) {
            state.creationFailed()
            throw exception
        }

        state.creationEnded(definition, argument, result)

        ranPostProcessorsIfObjectCreated(result, definition, argument)

        return result
    }

    private fun <TObject> ranPostProcessorsIfObjectCreated(
        optionalCreatedObject: TObject?,
        definition: ObjectCreatingDefinition<TObject>,
        argument: Any?
    ) {
        if (optionalCreatedObject != null) {
            ranPostProcessors(optionalCreatedObject as Any, definition, argument)
        }
    }

    private fun <TObject> ranPostProcessors(
        createdObject: Any,
        definition: ObjectCreatingDefinition<TObject>,
        argument: Any?
    ) {
        val name = DefinitionAndArgument(definition, argument).fullyQualifiedName()
        configuration.objectPostProcessors.forEach { processor -> processor.process(name, createdObject) }
    }

    override fun dispose() {
        state.dispose()
    }

    override fun close() {
        dispose()
    }

    override fun logCleanUpError(sourceObject: Any, operation: String, exception: Exception) {
        state.logCleanUpError(sourceObject, operation, exception)
    }

    internal fun addCreatedObject(definitionWithObject: ArgumentBoundDefinitionAndObject<*>) {
        objectsInCreationOrder.add(definitionWithObject)
        objectsToBeInitialized.add(definitionWithObject)
    }
}
