package org.ooverkommelig.examples.ooverkommelig.homepage

import org.ooverkommelig.ConstantDefinition
import java.io.File

fun main() {
    ApplicationOgd(object : ApplicationOgd.Provided {
        override fun applicationLogDirectory() = ConstantDefinition(File(System.getProperty("user.dir")))
    }).Graph().use { graph ->
        graph.someService().doYourThing()
    }
}
