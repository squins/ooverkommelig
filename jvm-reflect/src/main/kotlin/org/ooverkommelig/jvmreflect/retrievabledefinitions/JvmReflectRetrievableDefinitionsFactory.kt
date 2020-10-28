package org.ooverkommelig.jvmreflect.retrievabledefinitions

import org.ooverkommelig.RetrievableDefinitions
import org.ooverkommelig.RetrievableDefinitionsFactory
import org.ooverkommelig.SubGraphDefinition

object JvmReflectRetrievableDefinitionsFactory : RetrievableDefinitionsFactory {
    override fun create(owner: SubGraphDefinition): RetrievableDefinitions = JvmReflectRetrievableDefinitions(owner)
}
