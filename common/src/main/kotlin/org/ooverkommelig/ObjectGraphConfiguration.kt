package org.ooverkommelig

header class ObjectGraphConfiguration(
        logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
        objectPostProcessors: Collection<ObjectPostProcessor> = emptyList()) {
    internal val logger: ObjectGraphLogger
    internal val objectPostProcessors: Collection<ObjectPostProcessor>
}
