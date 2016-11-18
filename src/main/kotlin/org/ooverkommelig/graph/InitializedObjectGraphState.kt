package org.ooverkommelig.graph

import org.ooverkommelig.definition.ObjectCreatingDefinition
import org.ooverkommelig.opt
import java.util.Stack

internal class InitializedObjectGraphState : FollowingObjectGraphState {
    private lateinit var graph: ObjectGraphImpl
    private val definitionsOfObjectsBeingCreatedStack = Stack<DefinitionAndArgument<*>>()

    private val objectsToBeWired = mutableListOf<ArgumentBoundDefinitionAndObject<*>>()


    override fun enter(graph: ObjectGraphImpl) {
        this.graph = graph

        eagerlyCreateObjects()
    }

    private fun eagerlyCreateObjects() {
        graph.objectsToCreateEagerly.forEach { definition ->
            opt(definition)
        }
    }

    override fun creationStarted(definition: ObjectCreatingDefinition<*>, argument: Any?) {
        check(!wouldFormACycle(definition, argument), { getCycleDetectedMessage(definition, argument) })

        definitionsOfObjectsBeingCreatedStack.push(DefinitionAndArgument(definition, argument))
    }

    private fun wouldFormACycle(definition: ObjectCreatingDefinition<*>, argument: Any?) =
            definitionsOfObjectsBeingCreatedStack.any { element ->
                element.definition == definition && element.argument == argument
            }

    private fun getCycleDetectedMessage(definition: ObjectCreatingDefinition<*>, argument: Any?) =
            """Cycle detected (oldest to newest):
${getPreviousDefinitionFullyQualifiedNames()}
${DefinitionAndArgument(definition, argument).fullyQualifiedName()}"""

    private fun getPreviousDefinitionFullyQualifiedNames() =
            definitionsOfObjectsBeingCreatedStack
                    .map(DefinitionAndArgument<*>::fullyQualifiedName)
                    .joinToString("\n")

    override fun <TObject> creationEnded(definition: ObjectCreatingDefinition<TObject>, argument: Any?, createdObject: TObject?) {
        addObjectIfCreated(definition, argument, createdObject)
        // The set-up has to be done before the definition is popped to prevent the set-up from being run again when
        // new objects are created during the set-up of the current objects. The set-up of newly created objects will be
        // handled by the current set-up.
        runSetUpOfCreatedObjectsIfRootCreation()
        definitionsOfObjectsBeingCreatedStack.pop()
    }

    private fun <TObject> addObjectIfCreated(definition: ObjectCreatingDefinition<TObject>, argument: Any?, obj: TObject?) {
        obj?.let { obj -> addObject(definition, argument, obj) }
    }

    private fun <TObject> addObject(definition: ObjectCreatingDefinition<TObject>, argument: Any?, obj: TObject) {
        val definitionWithObject = ArgumentBoundDefinitionAndObject(definition, argument, obj)
        objectsToBeWired.add(definitionWithObject)
        graph.addCreatedObject(definitionWithObject)
    }

    private fun runSetUpOfCreatedObjectsIfRootCreation() {
        if (isRootCreation()) {
            runSetUpOfCreatedObjects()
        }
    }

    private fun runSetUpOfCreatedObjects() {
        while (hasNotFinishedSetUpOfAllObjects()) {
            wireObjects()
            setUpObjects(graph.objectsToBeInitialized, ArgumentBoundDefinitionAndObject<*>::init)
        }
    }

    private fun hasNotFinishedSetUpOfAllObjects() = !graph.objectsToBeInitialized.isEmpty()

    private fun wireObjects() {
        while (!objectsToBeWired.isEmpty()) {
            objectsToBeWired.removeAt(0).wire()
        }
    }

    private fun setUpObjects(objectsToSetUp: MutableList<ArgumentBoundDefinitionAndObject<*>>, setUpFunction: (ArgumentBoundDefinitionAndObject<*>) -> Unit) {
        while (haveNoObjectsBeenCreatedDuringSetUpAfterWiring() && !objectsToSetUp.isEmpty()) {
            setUpFunction(objectsToSetUp.removeAt(0))
        }
    }

    private fun haveNoObjectsBeenCreatedDuringSetUpAfterWiring() = objectsToBeWired.isEmpty()

    override fun creationFailed() {
        definitionsOfObjectsBeingCreatedStack.pop()
        // TODO: Document why this needs to be done after popping
        if (wasRootCreation()) {
            graph.transition(DisposingObjectGraphState())
        }
    }

    private fun isRootCreation() = definitionsOfObjectsBeingCreatedStack.size == 1

    private fun wasRootCreation() = definitionsOfObjectsBeingCreatedStack.isEmpty()

    override fun logCleanUpError(sourceObject: Any, operation: String, exception: Exception) = throw UnsupportedOperationException("Cannot clean up sub graphs and objects while initialized.")

    override fun dispose() {
        graph.transition(DisposingObjectGraphState())
    }
}
