package org.ooverkommelig.definition

internal data class ObjectlessLifecycle(val ownerName: String, val description: String, val init: () -> Unit, val dispose: () -> Unit) {
    override fun toString() = "$ownerName / $description"
}
