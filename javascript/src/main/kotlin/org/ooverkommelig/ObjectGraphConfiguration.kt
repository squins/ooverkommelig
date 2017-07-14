package org.ooverkommelig

impl class ObjectGraphConfiguration(
        impl internal val logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
        impl internal val objectPostProcessors: Collection<ObjectPostProcessor> = emptyList())
