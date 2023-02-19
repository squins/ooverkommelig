package org.ooverkommelig

actual class ObjectGraphConfiguration(
    internal actual val logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
    internal actual val objectPostProcessors: Collection<ObjectPostProcessor> = emptyList(),
    internal val aspectInvocationHandlerFactory: AspectInvocationHandlerFactory? = null,
    internal val retrievableDefinitionsFactory: RetrievableDefinitionsFactory = NoRetrievableDefinitionsFactory
) {
    actual constructor(
        logger: ObjectGraphLogger,
        objectPostProcessors: Collection<ObjectPostProcessor>
    ) :
            this(logger, objectPostProcessors, null, NoRetrievableDefinitionsFactory)
}
