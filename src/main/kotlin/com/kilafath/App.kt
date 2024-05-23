package com.kilafath

fun main(args: Array<String>) {
    val dayNumber = args.getOrElse(0) {"12"}
    val day = when(dayNumber) {
        in "01".."12" -> "com.kilafath.day$dayNumber.Day$dayNumber"
        else -> {
            println("input: $dayNumber :- either invalid or not solved yet. (input have to be of the form '01')")
            return
        }
    }
    Class.forName(day).getDeclaredConstructor().newInstance()
}
