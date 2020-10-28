package org.ooverkommelig.examples.ooverkommelig.homepage

import org.ooverkommelig.Definition
import org.ooverkommelig.Once
import org.ooverkommelig.SubGraphDefinition
import org.ooverkommelig.req
import java.io.File

// A sub graph definition is not self-contained, i.e. it is intended to be
// used in one or more object graphs. The object graph must provide the
// dependencies for the sub graphs it uses, either by using object definitions
// of other sub graphs or by requiring the object graph client to provide
// them.
//
// Sub graph definitions are the reusable pieces of objects and wiring that
// can be used in multiple contexts. For example:
// - The object definitions that are the same for both Android and iOS apps.
// - Infrastructure objects that are the same for all applications that you
//   build.
class ApplicationSgd(provided: Provided) : SubGraphDefinition() {
    interface Provided {
        fun logDirectory(): Definition<File>
    }

    // This is a very simple object definition as it only specifies the
    // creation lambda. OOverkommelig also support wiring (allowing object
    // cycles to be created), initialization and disposal.
    //
    // The creation lambda of this object will be invoked only once, and its
    // result will be returned to any other object needing the log file.
    // OOverkommelig supports a range of object definitions: constant, once,
    // always, parameterized once, parameterized always, aspect once (JVM
    // only) and aspect always (JVM only).
    //
    // Again: The values are public to reduce the amount of boilerplate, but
    // this is not a requirement.
    //
    // "req(...)" indicates that the dependency is required. OOverkommelig
    // also supports optional dependencies using "opt(...)".
    //
    // The object definitions allow enormous flexibility: You can use all
    // language constructs and all APIs to determine what should be
    // constructed, how it should be configured, etc. You can do much more
    // than shown in this simplistic example.
    val logFile by Once { File(req(provided.logDirectory()), "log.txt") }

    val log by Once { Log(req(logFile)) }

    val someService by Once { SomeService(req(log)) }
}
