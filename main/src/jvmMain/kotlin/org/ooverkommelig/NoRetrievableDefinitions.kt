package org.ooverkommelig

import kotlin.reflect.KProperty

object NoRetrievableDefinitions : RetrievableDefinitions {
    override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        // No action needed. This implementation does not provide retrievable definitions, so there is no need to store
        // anything.
    }

    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
        emptyList<Definition<TObject>>()
}
