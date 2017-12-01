package org.ooverkommelig

import kotlin.reflect.KProperty

actual abstract class SubGraphDefinition(
        provided: ProvidedBase,
        objectGraphConfiguration: ObjectGraphConfiguration = ObjectGraphConfiguration())
    : SubGraphDefinitionCommon(provided) {
    private val retrievableDefintions = objectGraphConfiguration.retrievableDefinitionsFactory.create(this)

    actual constructor(provided: ProvidedBase) : this(provided, ObjectGraphConfiguration())

    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
            super.transitiveRetrievableDefinitions(criteria) + retrievableDefintions.transitiveRetrievableDefinitions(criteria)

    actual override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        retrievableDefintions.addDefinitionProperty(property, returnsSameObjectForAllRetrievals)
    }
}
