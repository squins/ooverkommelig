package org.ooverkommelig.definition

actual abstract class SubGraphDefinitionOwner : SubGraphDefinitionOwnerCommon() {
    actual override val fullyQualifiedName: String?
        get() = null
}
