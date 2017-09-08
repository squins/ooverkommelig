package org.ooverkommelig

impl class ObjectGraphConfiguration(
        impl internal val logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
        impl internal val objectPostProcessors: Collection<ObjectPostProcessor> = emptyList(),
        internal val retrievableDefinitionsFactory: RetrievableDefinitionsFactory = ReflectionRetrievableDefinitionsFactory) {
    constructor(logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
                objectPostProcessors: Collection<ObjectPostProcessor> = emptyList()) :
            this(logger, objectPostProcessors, ReflectionRetrievableDefinitionsFactory)
}
