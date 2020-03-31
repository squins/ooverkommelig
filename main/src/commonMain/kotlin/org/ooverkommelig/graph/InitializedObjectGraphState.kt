package org.ooverkommelig.graph

import org.ooverkommelig.definition.ObjectCreatingDefinition
import org.ooverkommelig.opt

internal class InitializedObjectGraphState : FollowingObjectGraphState {
    private val rootObjectSetupFunction: InitializedObjectGraphState.() -> Unit = {
        wireObjectInWiringContext()
        initializeObjectsInInitializationContext()
    }
    private val wiringObjectSetupFunction: InitializedObjectGraphState.() -> Unit = { wireObjects() }
    private val initializationObjectSetupFunction: InitializedObjectGraphState.() -> Unit = { wireObjectInWiringContext() }

    private lateinit var graph: ObjectGraphImpl
    private val definitionsOfObjectsBeingCreatedStack = mutableListOf<DefinitionAndArgument<*>>()
    private var contextObjectSetupFunctionStack = mutableListOf(rootObjectSetupFunction)
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
        check(!wouldFormACycle(definition, argument)) { getCycleDetectedMessage(definition, argument) }

        definitionsOfObjectsBeingCreatedStack.add(DefinitionAndArgument(definition, argument))
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
            definitionsOfObjectsBeingCreatedStack.joinToString("\n", transform = DefinitionAndArgument<*>::fullyQualifiedName)

    override fun <TObject> creationEnded(definition: ObjectCreatingDefinition<TObject>, argument: Any?, createdObject: TObject?) {
        addObjectIfCreated(definition, argument, createdObject)
        definitionsOfObjectsBeingCreatedStack.removeLast()
        runSetUpOfCreatedObjectsIfRootCreation()
    }

    private fun <TObject> addObjectIfCreated(definition: ObjectCreatingDefinition<TObject>, argument: Any?, optionalObj: TObject?) {
        optionalObj?.let { obj -> addObject(definition, argument, obj) }
    }

    private fun <TObject> addObject(definition: ObjectCreatingDefinition<TObject>, argument: Any?, obj: TObject) {
        val definitionWithObject = ArgumentBoundDefinitionAndObject(definition, argument, obj)
        objectsToBeWired.add(definitionWithObject)
        graph.addCreatedObject(definitionWithObject)
    }

    private fun runSetUpOfCreatedObjectsIfRootCreation() {
        if (wasRootCreation()) {
            runSetUpOfCreatedObjects()
        }
    }

    private fun runSetUpOfCreatedObjects() {
        contextObjectSetupFunctionStack.last().invoke(this)
    }

    private fun wireObjectInWiringContext() {
        runInContext(wiringObjectSetupFunction) { wireObjects() }
    }

    private fun wireObjects() {
        while (objectsToBeWired.isNotEmpty()) {
            objectsToBeWired.removeAt(0).wire()
        }
    }

    private fun initializeObjectsInInitializationContext() {
        runInContext(initializationObjectSetupFunction) { initializeObjects() }
    }

    private fun initializeObjects() {
        while (graph.objectsToBeInitialized.isNotEmpty()) {
            graph.objectsToBeInitialized.removeAt(0).init()
        }
    }

    private fun runInContext(objectSetupFunction: InitializedObjectGraphState.() -> Unit, block: () -> Unit) {
        contextObjectSetupFunctionStack.add(objectSetupFunction)
        block()
        contextObjectSetupFunctionStack.removeLast()
    }

    override fun creationFailed() {
        definitionsOfObjectsBeingCreatedStack.removeLast()
        // TODO: Document why this needs to be done after popping
        if (wasRootCreation()) {
            graph.transition(DisposingObjectGraphState())
        }
    }

    private fun wasRootCreation() = definitionsOfObjectsBeingCreatedStack.isEmpty()

    override fun logCleanUpError(sourceObject: Any, operation: String, exception: Exception) = throw UnsupportedOperationException("Cannot clean up sub graphs and objects while initialized.")

    override fun dispose() {
        graph.transition(DisposingObjectGraphState())
    }

    companion object {
        private fun MutableList<*>.removeLast() {
            removeAt(lastIndex)
        }
    }
}
