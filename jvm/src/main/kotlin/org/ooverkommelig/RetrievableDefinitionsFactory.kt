package org.ooverkommelig

interface RetrievableDefinitionsFactory {
    fun create(owner: SubGraphDefinition): RetrievableDefinitions
}
