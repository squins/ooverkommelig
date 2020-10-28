package org.ooverkommelig

import kotlin.reflect.KProperty

expect abstract class SubGraphDefinition() : SubGraphDefinitionCommon {
    override fun addDefinitionProperty(property: KProperty<*>, returnsSameObjectForAllRetrievals: Boolean)
}
