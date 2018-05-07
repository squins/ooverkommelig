package org.ooverkommelig

actual class ObjectGraphConfiguration actual constructor(
        actual internal val logger: ObjectGraphLogger,
        actual internal val objectPostProcessors: Collection<ObjectPostProcessor>)
