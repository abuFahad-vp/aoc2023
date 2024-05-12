package com.kilafath

import java.io.File

const val Path = "src/main/kotlin/com/kilafath/"

fun readAsLines(path: String):List<String> {
    return File("$Path$path").readLines()
}
