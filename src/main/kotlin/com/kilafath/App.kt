package com.kilafath
import com.kilafath.day01.Day01
import com.kilafath.day02.Day02
import com.kilafath.day03.Day03

fun main(args: Array<String>) {
    val dayNumber = args.getOrElse(0) {"03"}
    when(dayNumber) {
        "01" -> Day01()
        "02" -> Day02()
        "03" -> Day03()
        else -> println("input: $dayNumber :- either invalid or not solved yet. Input have to be of the form '01'")
    }
}