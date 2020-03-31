package org.ooverkommelig.definition

expect abstract class SubGraphDefinitionOwner() : SubGraphDefinitionOwnerCommon {
    override val fullyQualifiedName: String?
}
