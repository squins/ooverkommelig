package org.ooverkommelig.jvmreflect.retrievabledefinitions

import org.ooverkommelig.Definition
import org.ooverkommelig.DefinitionCriteria
import org.ooverkommelig.RetrievableDefinitions
import org.ooverkommelig.SubGraphDefinition
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubtypeOf

internal class JvmReflectRetrievableDefinitions(private val owner: SubGraphDefinition) : RetrievableDefinitions {
    private val definitionProperties = mutableListOf<DefinitionProperty>()

    override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        @Suppress("UNCHECKED_CAST")
        definitionProperties += DefinitionProperty(
            property as KProperty<Definition<*>>,
            returnsSameObjectForAllRetrievals
        )
    }

    @Suppress("ConvertCallChainIntoSequence")
    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
        definitionProperties.filter { candidateDefinitionProperty ->
            candidateDefinitionProperty.type.isSubtypeOf(criteria.getType())
                    && (!criteria.mustReturnSameObjectForAllRetrievals
                    || candidateDefinitionProperty.returnsSameObjectForAllRetrievals)
        }.map { (definitionProperty) ->
            @Suppress("UNCHECKED_CAST")
            definitionProperty.getter.call(owner) as Definition<TObject>
        }
}
