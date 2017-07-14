package org.ooverkommelig

import kotlin.reflect.KProperty

interface RetrievableDefinitions {
    fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean)

    fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>): Collection<Definition<TObject>>
}
