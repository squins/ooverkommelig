package org.ooverkommelig

impl class ObjectGraphConfiguration impl constructor(
        impl internal val logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
        impl internal val objectPostProcessors: Collection<ObjectPostProcessor> = emptyList())
