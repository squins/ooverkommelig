package org.ooverkommelig

actual class ObjectGraphConfiguration actual constructor(
    internal actual val logger: ObjectGraphLogger,
    internal actual val objectPostProcessors: Collection<ObjectPostProcessor>
)
