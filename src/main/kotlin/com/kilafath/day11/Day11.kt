package com.kilafath.day11

import com.kilafath.readAsLines
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.max

class Day11 {
    init { readAsLines("day11/input.txt").solve() }

    private fun List<String>.solve() {
        val emptyRows = this.expandRow()
        val emptyCols = this.expandColumn()
        val galaxies = this.foldIndexed(listOf<Pair<Int,Int>>()) {x, acc, line ->
            acc + Regex("""#""").findAll(line).map { x to it.range.first }.toList()
        }

        var part1 = 0L
        var part2 = 0L
        for (i in 0..galaxies.lastIndex) {
            for (j in i+1..galaxies.lastIndex) {
                val (x1, y1) = galaxies[i]
                val (x2, y2) = galaxies[j]
                val dist = abs(x1 - x2) + abs(y1 - y2)
                val dx = emptyRows.count { min(x1,x2) < it && it < max(x1,x2) }
                val dy = emptyCols.count { min(y1,y2) < it && it < max(y1,y2) }
                part1 += dist + dx + dy
                part2 += dist + (1000000-1) * (dx + dy)
            }
        }
        println("part 1: $part1, part 2: $part2")
    }

    private fun List<String>.expandRow(): List<Int> {
        return this.foldIndexed(listOf()) {x, list, lines ->
            if (lines.all { it == '.' }) list + listOf(x) else list
        }
    }

    private fun List<String>.expandColumn(): List<Int> {
        var yCursor = 0
        val emptyCol = mutableListOf<Int>()
        while (yCursor < this[0].length) {
            var flag = true
            for (line in this) { if (line[yCursor] != '.') { flag = false; break } }
            if (flag) emptyCol.add(yCursor)
            yCursor++
        }
        return emptyCol.toList()
    }
}