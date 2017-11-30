package org.ooverkommelig

actual class ObjectGraphConfiguration actual constructor(
        actual internal val logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
        actual internal val objectPostProcessors: Collection<ObjectPostProcessor> = emptyList())
