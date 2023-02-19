package org.ooverkommelig.jvmreflect

import org.ooverkommelig.NoOperationObjectGraphLogger
import org.ooverkommelig.ObjectGraphConfiguration
import org.ooverkommelig.ObjectGraphLogger
import org.ooverkommelig.ObjectPostProcessor
import org.ooverkommelig.jvmreflect.aspects.JvmReflectAspectInvocationHandlerFactory
import org.ooverkommelig.jvmreflect.retrievabledefinitions.JvmReflectRetrievableDefinitionsFactory

fun jvmReflectObjectGraphConfiguration(
    logger: ObjectGraphLogger = NoOperationObjectGraphLogger,
    objectPostProcessors: Collection<ObjectPostProcessor> = emptyList()
) =
    ObjectGraphConfiguration(
        logger,
        objectPostProcessors,
        JvmReflectAspectInvocationHandlerFactory,
        JvmReflectRetrievableDefinitionsFactory
    )
