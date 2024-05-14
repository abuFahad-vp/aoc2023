package com.kilafath

import java.io.File

const val Path = "src/main/kotlin/com/kilafath/"

fun readAsLines(path: String):List<String> {
    return File("$Path$path").readLines()
}

fun readAsString(path: String): String {
    return File("$Path$path").readText()
}

infix fun <A, B, C> ((A) -> B).then(other: (B) -> C): (A) -> C {
    return { other(this(it)) }
}
