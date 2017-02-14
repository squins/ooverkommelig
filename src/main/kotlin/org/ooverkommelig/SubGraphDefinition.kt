package org.ooverkommelig

import org.ooverkommelig.definition.ObjectCreatingDefinition
import org.ooverkommelig.definition.ObjectlessLifecycle
import org.ooverkommelig.definition.SubGraphDefinitionOwner

abstract class SubGraphDefinition(provided: ProvidedBase = NothingProvidedAdministration) : SubGraphDefinitionOwner() {
    private val objectlessLifecycles = mutableListOf<ObjectlessLifecycle>()

    private var owner: SubGraphDefinitionOwner? = null

    init {
        provided.setOwner(this)
    }

    internal fun setOwner(newOwner: SubGraphDefinitionOwner) {
        check(owner == null, { "Tried to set the owner multiple times." })

        owner = newOwner
    }

    override val objectGraphDefinition: ObjectGraphDefinition
        get() = owner?.objectGraphDefinition ?: throw IllegalStateException("Owner of: $name, has not been initialized. Use 'add(...)' to add the sub graph to its owner when you create it.")

    protected fun lifecycle(description: String, init: () -> Unit, dispose: () -> Unit) {
        objectlessLifecycles += ObjectlessLifecycle(name, description, init, dispose)
    }

    // Whether an object is eagerly created or not is not specified at its definition, because there is no way to know
    // when a definition is complete (i.e. when the last method on the delegate has been invoked). And registration
    // during creation of the definition is too late. I.e. only the delegate could register the definition if it knew
    // when the definition is done (i.e. no more method invocations on itself).
    protected open fun objectsToCreateEagerly() = listOf<Definition<*>>()

    override fun allObjectlessLifecycles() = super.allObjectlessLifecycles() + objectlessLifecycles

    override fun allObjectsToCreateEagerly() = super.allObjectsToCreateEagerly() + objectsToCreateEagerly()

    internal fun <TObject> handleCreation(definition: ObjectCreatingDefinition<TObject>, argument: Any?, creator: () -> TObject) =
            objectGraphDefinition.handleCreation(definition, argument, creator)
}
