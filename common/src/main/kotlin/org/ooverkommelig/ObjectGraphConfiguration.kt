package org.ooverkommelig

data class ObjectGraphConfiguration(
        internal val logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
        internal val objectPostProcessors: Collection<ObjectPostProcessor> = emptyList())
