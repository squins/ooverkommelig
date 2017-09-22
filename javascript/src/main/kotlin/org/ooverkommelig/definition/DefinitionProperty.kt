package org.ooverkommelig.definition

import org.ooverkommelig.Definition
import kotlin.reflect.KProperty

impl internal data class DefinitionProperty impl constructor(impl val property: KProperty<Definition<*>>, impl val returnsSameObjectForAllRetrievals: Boolean)
