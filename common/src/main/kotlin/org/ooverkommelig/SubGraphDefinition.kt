package org.ooverkommelig

import kotlin.reflect.KProperty

expect abstract class SubGraphDefinition(provided: ProvidedBase) : SubGraphDefinitionCommon {
    override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean)
}
