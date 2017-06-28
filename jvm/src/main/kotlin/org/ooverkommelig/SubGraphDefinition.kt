package org.ooverkommelig

import kotlin.reflect.full.isSubtypeOf

impl abstract class SubGraphDefinition(provided: ProvidedBase) : SubGraphDefinitionCommon(provided) {
    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
            super.transitiveRetrievableDefinitions(criteria) + definitionProperties.filter { candidateDefinitionProperty ->
                candidateDefinitionProperty.type.isSubtypeOf(criteria.objectType.asKType())
                        && (!criteria.mustReturnSameObjectForAllRetrievals
                        || candidateDefinitionProperty.returnsSameObjectForAllRetrievals)
            }.map { definitionProperty ->
                @Suppress("UNCHECKED_CAST")
                definitionProperty.property.getter.call(this) as Definition<TObject>
            }
}
