package org.ooverkommelig

import org.ooverkommelig.definition.DelegateOfObjectToCreateEagerly
import org.ooverkommelig.definition.ObjectCreatingDefinition
import org.ooverkommelig.definition.ObjectlessLifecycle
import org.ooverkommelig.definition.SubGraphDefinitionOwner
import org.ooverkommelig.definition.SubGraphDefinitionOwnerCommon
import kotlin.reflect.KProperty

abstract class SubGraphDefinitionCommon(provided: ProvidedBase) : SubGraphDefinitionOwner() {
    private val objectlessLifecycles = mutableListOf<ObjectlessLifecycle>()
    
    internal val delegatesOfObjectsToCreateEagerly = mutableListOf<DelegateOfObjectToCreateEagerly<*>>()

    private var owner: SubGraphDefinitionOwnerCommon? = null

    init {
        provided.setOwner(this)
    }

    internal fun setOwner(newOwner: SubGraphDefinitionOwnerCommon) {
        check(owner == null, { "Tried to set the owner multiple times." })

        owner = newOwner
    }

    override val objectGraphDefinition: ObjectGraphDefinition
        get() = owner?.objectGraphDefinition ?: throw IllegalStateException("Owner of: $name, has not been initialized. Use 'add(...)' to add the sub graph to its owner when you create it.")

    protected fun lifecycle(description: String, init: () -> Unit, dispose: () -> Unit) {
        objectlessLifecycles += ObjectlessLifecycle(name, description, init, dispose)
    }

    override fun allObjectlessLifecycles() = super.allObjectlessLifecycles() + objectlessLifecycles

    override fun allObjectsToCreateEagerly() = super.allObjectsToCreateEagerly() + delegatesOfObjectsToCreateEagerly.map { delegate -> delegate.getValue() }

    abstract internal fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean)

    internal fun <TObject> handleCreation(definition: ObjectCreatingDefinition<TObject>, argument: Any?, creator: () -> TObject) =
            objectGraphDefinition.handleCreation(definition, argument, creator)
}
