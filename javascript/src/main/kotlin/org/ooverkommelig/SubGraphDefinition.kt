package org.ooverkommelig

import kotlin.reflect.KProperty

impl abstract class SubGraphDefinition(provided: ProvidedBase) : SubGraphDefinitionCommon(provided) {
    override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        // No action needed. JavaScript does/can not use the definition properties.
    }
}
