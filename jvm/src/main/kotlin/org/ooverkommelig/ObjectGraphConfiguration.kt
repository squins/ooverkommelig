package org.ooverkommelig

actual class ObjectGraphConfiguration(
        actual internal val logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
        actual internal val objectPostProcessors: Collection<ObjectPostProcessor> = emptyList(),
        internal val retrievableDefinitionsFactory: RetrievableDefinitionsFactory = ReflectionRetrievableDefinitionsFactory) {
    actual constructor(logger: ObjectGraphLogger,
                       objectPostProcessors: Collection<ObjectPostProcessor>) :
            this(logger, objectPostProcessors, ReflectionRetrievableDefinitionsFactory)
}
