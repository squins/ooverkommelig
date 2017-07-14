package org.ooverkommelig

object NoRetrievableDefinitionsFactory : RetrievableDefinitionsFactory {
    override fun create(owner: SubGraphDefinition) = NoRetrievableDefinitions
}
