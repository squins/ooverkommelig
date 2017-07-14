package org.ooverkommelig

import org.ooverkommelig.definition.DefinitionProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubtypeOf

class ReflectionRetrievableDefinitions(private val owner: SubGraphDefinition) : RetrievableDefinitions {
    private val definitionProperties = mutableListOf<DefinitionProperty>()

    override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        @Suppress("UNCHECKED_CAST")
        definitionProperties += DefinitionProperty(property as KProperty<Definition<*>>, returnsSameObjectForAllRetrievals)
    }

    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
            definitionProperties.filter { candidateDefinitionProperty ->
                candidateDefinitionProperty.type.isSubtypeOf(criteria.objectType.asKType())
                        && (!criteria.mustReturnSameObjectForAllRetrievals
                        || candidateDefinitionProperty.returnsSameObjectForAllRetrievals)
            }.map { definitionProperty ->
                @Suppress("UNCHECKED_CAST")
                definitionProperty.property.getter.call(owner) as Definition<TObject>
            }
}
