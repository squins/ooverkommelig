package org.ooverkommelig

object NoOperationObjectGraphLogger : ObjectGraphLogger {
    override fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception) {
        // No action needed. This logger does nothing.
    }
}
