package org.ooverkommelig

interface ObjectGraphLogger {
    fun errorDuringCleanUp(sourceObject: Any, operation: String, exception: Exception)
}
