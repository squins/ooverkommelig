package org.ooverkommelig

import kotlin.reflect.KProperty

actual abstract class SubGraphDefinition(
        provided: ProvidedBase,
        val objectGraphConfiguration: ObjectGraphConfiguration = ObjectGraphConfiguration())
    : SubGraphDefinitionCommon(provided) {
    private val retrievableDefintions: RetrievableDefinitions

    actual constructor(provided: ProvidedBase) : this(provided, ObjectGraphConfiguration())

    init {
        retrievableDefintions = objectGraphConfiguration.retrievableDefinitionsFactory.create(this)
    }

    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
            super.transitiveRetrievableDefinitions(criteria) + retrievableDefintions.transitiveRetrievableDefinitions(criteria)

    actual override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        retrievableDefintions.addDefinitionProperty(property, returnsSameObjectForAllRetrievals)
    }
}
