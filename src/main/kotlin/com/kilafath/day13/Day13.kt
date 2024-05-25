package com.kilafath.day13

import com.kilafath.readAsString

class Day13  {
    init {
        val input = readAsString("day13/input.txt").split("\n\r")
        val (part1, part2) = input.fold(Pair(0,0)) { acc, it ->
            val row = it.toRowBits()
            val col = it.toColBits()
            val value = row.mirrorValue(true) + col.mirrorValue()
            val smudgeValue = row.mirrorValue(true, isSmudge = true) +
                    col.mirrorValue(isSmudge = true)
            Pair(acc.first + value, acc.second + smudgeValue)
        }
        println("part 1: $part1, part 2: $part2")
    }

    class SmudgeCount(var canSmudge: Boolean = true, val smudgable: Boolean)
    private fun List<Int>.mirrorValue(isRow: Boolean=false, isSmudge: Boolean = false):Int {
        for (x in 0..<this.lastIndex) {
            val smudged = SmudgeCount(canSmudge = isSmudge, smudgable = isSmudge)
            if (this[x] == this[x+1] || !isNotSmudge(this[x],this[x+1],smudged)) {
                var counter = 1
                var flag = true
                while (x-counter >= 0 && x + 1 + counter <= this.lastIndex) {
                    if (this[x-counter] != this[x+1+counter]) {
                        if (smudged.smudgable) {
                            if (isNotSmudge(this[x - counter], this[x + 1 + counter], smudged)) {
                                flag = false
                                break
                            }
                        } else { flag = false; break }
                    }
                    counter++
                }
                if (flag && !smudged.canSmudge) {
                    return if (isRow) (x + 1) * 100 else x + 1
                }
            }
        }
        return 0
    }

    private fun isNotSmudge(n1: Int, n2: Int, smudge: SmudgeCount): Boolean {
        val n = n1 xor n2
        if (smudge.smudgable && smudge.canSmudge && n > 0 && (n and (n-1) == 0)) {
            smudge.canSmudge = false
            return false
        }
        return true
    }

    private fun String.toColBits():List<Int> {
        val block = this.trim().split("\n").map {it.trim()}
        val cols = mutableListOf<Int>()
        for (y in 0..block[0].lastIndex) {
            var line = ""
            for (x in 0..block.lastIndex) {
                line = when(block[x][y]) {
                    '.' -> line + "0"
                    '#' -> line + "1"
                    else -> throw Exception("Invalid character ${block[x][y]}")
                }
            }
            cols.add(line.toInt(2))
        }
        return cols.toList()
    }

    private fun String.toRowBits(): List<Int> {
        return this.trim().split("\n").map { char ->
            char.trim().map {
                when(it) {
                    '.' -> '0'
                    '#' -> '1'
                    else -> throw Exception("invalid character '$it'")
                }
            }.joinToString("").toInt(2)
        }
    }
}