package org.ooverkommelig

object ReflectionRetrievableDefinitionsFactory : RetrievableDefinitionsFactory {
    override fun create(owner: SubGraphDefinition) = ReflectionRetrievableDefinitions(owner)
}
