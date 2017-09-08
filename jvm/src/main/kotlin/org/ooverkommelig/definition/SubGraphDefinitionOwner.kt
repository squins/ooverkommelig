package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.DefinitionCriteria

impl abstract class SubGraphDefinitionOwner : SubGraphDefinitionOwnerCommon() {
    impl override val fullyQualifiedName: String?
        get() = this::class.java.name

    open fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>): Collection<Definition<TObject>> =
            subGraphs.flatMap { subGraph -> subGraph.transitiveRetrievableDefinitions(criteria) }
}
