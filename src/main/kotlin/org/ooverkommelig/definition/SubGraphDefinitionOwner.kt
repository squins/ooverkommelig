package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.DefinitionCriteria
import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.SubGraphDefinition

abstract class SubGraphDefinitionOwner {
    internal val subGraphs = mutableListOf<SubGraphDefinition>()

    private var isLocked = false

    open fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>): Collection<Definition<TObject>> =
            subGraphs.flatMap { subGraph -> subGraph.transitiveRetrievableDefinitions(criteria) }

    internal val name = this::class.qualifiedName ?: this::class.simpleName ?: "?"

    internal abstract val objectGraphDefinition: ObjectGraphDefinition

    protected fun <TSubGraph : SubGraphDefinition> add(subGraph: TSubGraph): TSubGraph {
        check(!isLocked, { "Cannot add definition to: $name, anymore, because the object graph has been created." })

        subGraph.setOwner(this)
        subGraphs.add(subGraph)
        return subGraph
    }

    internal fun lockDefinition() {
        isLocked = true
        subGraphs.forEach(SubGraphDefinitionOwner::lockDefinition)
    }

    internal open fun allObjectlessLifecycles(): List<ObjectlessLifecycle> = subGraphs.flatMap(SubGraphDefinition::allObjectlessLifecycles)

    internal open fun allObjectsToCreateEagerly(): List<Definition<*>> = subGraphs.flatMap(SubGraphDefinition::allObjectsToCreateEagerly)
}
