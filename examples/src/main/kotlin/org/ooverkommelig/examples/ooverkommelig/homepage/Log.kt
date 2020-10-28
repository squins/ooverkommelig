package org.ooverkommelig.examples.ooverkommelig.homepage

import java.io.File

class Log(private val file: File) {
    fun log(text: String) {
        println("Logging: $text, to file: $file")
    }
}
