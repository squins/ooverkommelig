package org.ooverkommelig.definition

// https://youtrack.jetbrains.com/issue/KT-17518
/*internal*/ data class ObjectlessLifecycle(val ownerName: String, val description: String, val init: () -> Unit, val dispose: () -> Unit) {
    override fun toString() = "$ownerName / $description"
}
