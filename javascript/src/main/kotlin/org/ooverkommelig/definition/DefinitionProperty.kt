package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import kotlin.reflect.KProperty

internal actual data class DefinitionProperty actual constructor(actual val property: KProperty<Definition<*>>, actual val returnsSameObjectForAllRetrievals: Boolean)
