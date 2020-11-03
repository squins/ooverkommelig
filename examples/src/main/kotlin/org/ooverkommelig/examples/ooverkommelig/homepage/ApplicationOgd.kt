package org.ooverkommelig.examples.ooverkommelig.homepage

import org.ooverkommelig.Definition
import org.ooverkommelig.ObjectGraphDefinition
import org.ooverkommelig.req
import java.io.File

// An object graph definition is a self-contained unit consisting of multiple
// object definitions, i.e. it can be instantiated.
//
// An single interface parameter with one or more functions provides external
// objects (or object definitions), allowing configuration of the object
// graph.
class ApplicationOgd(provided: Provided) : ObjectGraphDefinition() {
    // Explicit declaration of the dependencies of an object graph, so clients
    // of the graph quickly know what they need to provide to be able to use
    // the graph.
    //
    // Functions instead of values are used so to reduce the boilerplate in
    // certain situations. For example, when using a definition of one sub
    // graph for the dependency of another sub graph of the same parent graph.
    // See `org.ooverkommelig.examples.dagger.simple.CoffeeAppOgd` in the
    // examples.
    //
    // "Definition" can occur quite a number of times in (sub) graph
    // definitions. Its type alias "D" can be used to reduce the amount of
    // boilerplate.
    interface Provided {
        fun applicationLogDirectory(): Definition<File>
    }

    // This is "public", which is not a requirement but reduces the amount of
    // boilerplate. In this case all dependencies have to be provided by the
    // client of "ApplicationOgd". But it is also possible that
    // "ApplicationOgd" provides dependencies by using multiple sub graph
    // definitions, and using an object definition from one for the dependency
    // of another.
    val appSgd = add(ApplicationSgd(object : ApplicationSgd.Provided {
        override fun logDirectory() = provided.applicationLogDirectory()
    }))

    // By creating an instance of this class and requesting one or more
    // objects, the defined object will actually be created. Note that
    // OOverkommelig also allows for specifying eagerly created objects that
    // will be created once a graph instance is created.
    //
    // The graph instance implements "Closeable", which enables clean-up of
    // objects in the graph. When you only need a graph for a single operation
    // you can use the following code. This ensures that all objects are
    // disposed of once the operation finished:
    //     ApplicationOgd(object : ApplicationOgd.Provided { ... })
    //             .Graph().use { graph ->
    //                  graph.someService().doYourThing()
    //             }
    //
    // In cases where you need the graph for multiple operations, you create
    // and store it. When it is no longer needed, you dispose of it.
    inner class Graph : DefinitionObjectGraph() {
        fun someService() = req(appSgd.someService)
    }
}
