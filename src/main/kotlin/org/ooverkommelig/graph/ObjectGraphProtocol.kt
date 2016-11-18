package org.ooverkommelig.graph

internal interface ObjectGraphProtocol {
    fun logCleanUpError(sourceObject: Any, operation: String, exception: Exception)

    fun dispose()
}
