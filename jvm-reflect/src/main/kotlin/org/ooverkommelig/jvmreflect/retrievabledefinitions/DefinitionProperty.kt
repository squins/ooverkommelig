package org.ooverkommelig.jvmreflect.retrievabledefinitions

import org.ooverkommelig.Definition
import kotlin.reflect.KProperty

internal data class DefinitionProperty(val property: KProperty<Definition<*>>, val returnsSameObjectForAllRetrievals: Boolean) {
    val type = property.returnType.arguments[0].type ?: throw IllegalStateException()
}
