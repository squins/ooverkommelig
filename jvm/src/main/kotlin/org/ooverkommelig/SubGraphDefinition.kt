package org.ooverkommelig

import org.ooverkommelig.definition.DefinitionProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubtypeOf

impl abstract class SubGraphDefinition(provided: ProvidedBase) : SubGraphDefinitionCommon(provided) {
    private val definitionProperties = mutableListOf<DefinitionProperty>()

    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
            super.transitiveRetrievableDefinitions(criteria) + definitionProperties.filter { candidateDefinitionProperty ->
                candidateDefinitionProperty.type.isSubtypeOf(criteria.objectType.asKType())
                        && (!criteria.mustReturnSameObjectForAllRetrievals
                        || candidateDefinitionProperty.returnsSameObjectForAllRetrievals)
            }.map { definitionProperty ->
                @Suppress("UNCHECKED_CAST")
                definitionProperty.property.getter.call(this) as Definition<TObject>
            }

    override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        @Suppress("UNCHECKED_CAST")
        definitionProperties += DefinitionProperty(property as KProperty<Definition<*>>, returnsSameObjectForAllRetrievals)
    }
}
