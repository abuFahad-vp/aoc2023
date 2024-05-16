package com.kilafath.day06

import com.kilafath.*
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.math.ceil

class Day06 {
    init {
        readAsLines("day06/input.txt").solve()
    }

    private fun List<String>.solve() {
        val rgxNum = Regex("""\d+""")
        val timeRaw = rgxNum.findAll(this[0]).map {it.value}
        val distanceRaw = rgxNum.findAll(this[1]).map {it.value}
        val part1 = timeRaw.foldIndexed(1.0) { ix, acc, tm ->
            acc * numberOfWaysToWin(tm,distanceRaw.elementAt(ix))
        }
        val part2 = numberOfWaysToWin(timeRaw.joinToString(""), distanceRaw.joinToString(""))
        println("part1: ${part1.toInt()}, part2: ${part2.toInt()}")
    }

    private fun numberOfWaysToWin(tm: String, dstn: String): Double {
        val time = tm.toDouble()
        val distance = dstn.toDouble()
        val x1 = (-time + sqrt(time*time - 4*distance)) / -2
        val x2 = (-time - sqrt(time*time - 4*distance)) / -2
        return ceil(x2) - floor(x1) - 1
    }
}