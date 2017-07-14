package org.ooverkommelig

import kotlin.reflect.KProperty

impl abstract class SubGraphDefinition(
        provided: ProvidedBase,
        val objectGraphConfiguration: ObjectGraphConfiguration = ObjectGraphConfiguration())
    : SubGraphDefinitionCommon(provided) {
    private val retrievableDefintions: RetrievableDefinitions

    constructor(provided: ProvidedBase) : this(provided, ObjectGraphConfiguration())

    init {
        retrievableDefintions = objectGraphConfiguration.retrievableDefinitionsFactory.create()
    }

    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
            super.transitiveRetrievableDefinitions(criteria) + retrievableDefintions.transitiveRetrievableDefinitions(criteria)

    override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        retrievableDefintions.addDefinitionProperty(property, returnsSameObjectForAllRetrievals)
    }
}
