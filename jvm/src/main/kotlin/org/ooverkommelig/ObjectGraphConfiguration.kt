package org.ooverkommelig

actual class ObjectGraphConfiguration(
        actual internal val logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
        actual internal val objectPostProcessors: Collection<ObjectPostProcessor> = emptyList(),
        internal val retrievableDefinitionsFactory: RetrievableDefinitionsFactory = ReflectionRetrievableDefinitionsFactory) {
    actual constructor(logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
                       objectPostProcessors: Collection<ObjectPostProcessor> = emptyList()) :
            this(logger, objectPostProcessors, ReflectionRetrievableDefinitionsFactory)
}
