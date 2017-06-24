package org.ooverkommelig

import org.ooverkommelig.definition.DefinitionProperty
import org.ooverkommelig.definition.ObjectCreatingDefinition
import org.ooverkommelig.definition.ObjectlessLifecycle
import org.ooverkommelig.definition.SubGraphDefinitionOwner
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubtypeOf

abstract class SubGraphDefinition(provided: ProvidedBase) : SubGraphDefinitionOwner() {
    private val definitionProperties = mutableListOf<DefinitionProperty>()

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

    internal fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        @Suppress("UNCHECKED_CAST")
        definitionProperties += DefinitionProperty(property as KProperty<Definition<*>>, returnsSameObjectForAllRetrievals)
    }

    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
            super.transitiveRetrievableDefinitions(criteria) + definitionProperties.filter { candidateDefinitionProperty ->
                candidateDefinitionProperty.type.isSubtypeOf(criteria.objectType.asKType())
                        && (!criteria.mustReturnSameObjectForAllRetrievals
                        || candidateDefinitionProperty.returnsSameObjectForAllRetrievals)
            }.map { definitionProperty ->
                @Suppress("UNCHECKED_CAST")
                definitionProperty.property.getter.call(this) as Definition<TObject>
            }

    internal fun <TObject> handleCreation(definition: ObjectCreatingDefinition<TObject>, argument: Any?, creator: () -> TObject) =
            objectGraphDefinition.handleCreation(definition, argument, creator)
}
