package com.kilafath.day01

import com.kilafath.*

class Day01 {
    private val words = listOf(
            "0","1","2","3","4","5","6","7","8","9",
            "zero","one","two","three","four","five","six","seven","eight","nine"
    )

    init {
        readAsLines("day01/input.txt").solve()
    }

    private fun List<String>.solve() {
        val ans:Pair<Int,Int> = this.fold(Pair(0,0)) {acc, line ->
            Pair(acc.first + line.calibrate(
            forFirst = {it.first { c -> c.isDigit()}.digitToInt()},
            forSecond = {it.last  { c -> c.isDigit()}.digitToInt()},
            ), acc.second + line.calibrate(
                forFirst = {it.findAnyOf(words)?.second?.digitWordToInt()?:0},
                forSecond = {it.findLastAnyOf(words)?.second?.digitWordToInt()?:0},
            ))
        }
        println("part 1 = ${ans.first}, part 2 = ${ans.second}")
    }

    private fun String.calibrate(forFirst: (String) -> Int, forSecond: (String) -> Int):Int =
        forFirst(this) * 10 + forSecond(this)
    private fun String.digitWordToInt() = words.indexOf(this) % 10
}