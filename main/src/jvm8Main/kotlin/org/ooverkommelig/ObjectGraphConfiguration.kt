package org.ooverkommelig

actual class ObjectGraphConfiguration(
        internal actual val logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
        internal actual val objectPostProcessors: Collection<ObjectPostProcessor> = emptyList(),
        internal val retrievableDefinitionsFactory: RetrievableDefinitionsFactory = ReflectionRetrievableDefinitionsFactory) {
    actual constructor(logger: ObjectGraphLogger,
                       objectPostProcessors: Collection<ObjectPostProcessor>) :
            this(logger, objectPostProcessors, ReflectionRetrievableDefinitionsFactory)
}
