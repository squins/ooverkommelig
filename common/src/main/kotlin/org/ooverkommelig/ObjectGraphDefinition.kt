package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinition
import org.ooverkommelig.definition.SubGraphDefinitionOwner
import org.ooverkommelig.graph.ObjectGraphImpl

abstract class ObjectGraphDefinition(provided: ProvidedBase, private val objectGraphConfiguration: ObjectGraphConfiguration = ObjectGraphConfiguration()) : SubGraphDefinitionOwner() {
    private var objectGraphImplementation: ObjectGraphImpl? = null

    init {
        provided.setOwner(this)
    }

    override val objectGraphDefinition: ObjectGraphDefinition
        get() = this

    abstract inner class DefinitionObjectGraph : ObjectGraph by create()

    private fun create(): ObjectGraph {
        check(objectGraphImplementation == null, { "Object graph has already been created." })

        lockDefinition()

        val result = ObjectGraphImpl(objectGraphConfiguration, allObjectlessLifecycles(), allObjectsToCreateEagerly())
        objectGraphImplementation = result
        result.init()

        return result
    }

    fun hasGraphBeenCreated() = objectGraphImplementation != null

    fun assertGraphHasBeenCreated() {
        checkNotNull(objectGraphImplementation, { "The graph of this definition is expected to have been created at this point." })
    }

    internal fun <TObject> handleCreation(definition: ObjectCreatingDefinition<TObject>, argument: Any?, creator: () -> TObject): TObject {
        val currentObjectGraphImplementation = objectGraphImplementation ?: throw IllegalStateException("The object graph has not been created yet.")
        return currentObjectGraphImplementation.handleCreation(definition, argument, creator)
    }
}
