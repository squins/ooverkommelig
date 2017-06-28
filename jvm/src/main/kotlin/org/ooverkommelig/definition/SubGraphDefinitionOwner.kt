package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import org.ooverkommelig.DefinitionCriteria

impl abstract class SubGraphDefinitionOwner : SubGraphDefinitionOwnerCommon() {
    impl override val fullyQualifiedName = this::class.qualifiedName

    open fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>): Collection<Definition<TObject>> =
            subGraphs.flatMap { subGraph -> subGraph.transitiveRetrievableDefinitions(criteria) }
}
