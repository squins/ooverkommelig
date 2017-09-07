package org.ooverkommelig.examples.ooverkommelig.lifecycle

import org.ooverkommelig.Once
import org.ooverkommelig.ProvidedBase
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req

class LifecycleSgd(provided: Provided) : SubGraphDefinition(provided) {
    interface Provided : ProvidedBase

    val service by Once {
        // The secondary service is considered to be created before this service because this service
        // retrieves it in its creation function. By retrieving it before the print statement, the
        // output will be correct.
        req(secondaryService)
        println("PRIMARY Creation")
        Any()
    }.wire {
        println("PRIMARY Wiring")
    }.init {
        println("PRIMARY Initialization")
    }.dispose {
        println("PRIMARY Disposal")
    }

    val secondaryService by Once {
        println("SECONDARY Creation")
        ServiceWithBackgroundTask()
    }.wire {
        println("SECONDARY Wiring")
    }.init {
        println("SECONDARY Initialization")
        it.start()
    }.dispose {
        println("SECONDARY Disposal")
        it.stop()
    }
}
