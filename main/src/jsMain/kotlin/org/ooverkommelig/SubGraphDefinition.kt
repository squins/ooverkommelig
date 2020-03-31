package org.ooverkommelig

import kotlin.reflect.KProperty

actual abstract class SubGraphDefinition actual constructor(provided: ProvidedBase) : SubGraphDefinitionCommon(provided) {
    actual override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean) {
        // No action needed. JavaScript does/can not use the definition properties.
    }
}
