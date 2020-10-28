package org.ooverkommelig

import kotlin.reflect.KProperty

actual abstract class SubGraphDefinition(
        private val objectGraphConfiguration: ObjectGraphConfiguration = ObjectGraphConfiguration())
    : SubGraphDefinitionCommon() {
    private val retrievableDefinitions = objectGraphConfiguration.retrievableDefinitionsFactory.create(this)

    actual constructor() : this(ObjectGraphConfiguration())

    override fun <TObject> transitiveRetrievableDefinitions(criteria: DefinitionCriteria<TObject>) =
            super.transitiveRetrievableDefinitions(criteria) + retrievableDefinitions.transitiveRetrievableDefinitions(criteria)

    actual override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        retrievableDefinitions.addDefinitionProperty(property, returnsSameObjectForAllRetrievals)
    }

    internal fun supportsAspects() = objectGraphConfiguration.aspectInvocationHandlerFactory != null

    internal fun getAspectInvocationHandlerFactory() =
            objectGraphConfiguration.aspectInvocationHandlerFactory ?: throw IllegalStateException()
}
