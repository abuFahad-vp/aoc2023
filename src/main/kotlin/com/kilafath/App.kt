package com.kilafath
import com.kilafath.day01.Day01
import com.kilafath.day02.Day02

fun main(args: Array<String>) {
    val dayNumber = args.getOrElse(0) {"02"}
    when(dayNumber) {
        "01" -> Day01()
        "02" -> Day02()
        else -> println("input: $dayNumber :- either invalid or not solved yet. Input have to be of the form '01'")
    }
}