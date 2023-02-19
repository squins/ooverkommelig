package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.DefinitionCriteria

actual abstract class SubGraphDefinitionOwner : SubGraphDefinitionOwnerCommon() {
    actual override val fullyQualifiedName: String?
        get() = this::class.java.name

    open fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>): Collection<Definition<TObject>> =
        subGraphs.flatMap { subGraph -> subGraph.transitiveRetrievableDefinitions(criteria) }
}
