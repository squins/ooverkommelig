package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import kotlin.reflect.KProperty

expect internal class DefinitionProperty(property: KProperty<Definition<*>>, returnsSameObjectForAllRetrievals: Boolean) {
    val property: KProperty<Definition<*>>
    val returnsSameObjectForAllRetrievals: Boolean
}
