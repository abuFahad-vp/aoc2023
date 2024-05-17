package com.kilafath.day08

import com.kilafath.*
import org.apache.commons.math3.util.ArithmeticUtils

class Day08 {

    private val terrain = mutableMapOf<String, Pair<String,String>>()
    private var stepSequence = ""
    init {
        readAsLines("day08/input.txt").solve()
    }
    private fun List<String>.solve() {
        stepSequence = this[0]
        val startNodes = this.drop(2).fold(listOf<String>()){ acc, code ->
            val start = Regex("""\w+(?= )""").findAll(code).elementAt(0).value
            val left = Regex("""(?<=\()\w+""").findAll(code).elementAt(0).value
            val right = Regex("""\w+(?=\))""").findAll(code).elementAt(0).value
            terrain[start] = Pair(left,right)
            if (start[2] == 'A')  acc + start else acc
        }

        val part1 = traverse()
        val part2 = startNodes.map {node ->
            traverse(node, endPattern = Regex("""\w\wZ"""))
        }.fold(1L) {acc, steps ->
            ArithmeticUtils.lcm(acc,steps)
        }
        println("part1: $part1, part2: $part2")
    }

    private tailrec fun traverse(
        currentNode: String = "AAA",
        endPattern: Regex = Regex("""ZZZ"""), steps: Long = 0L): Long {
        if (endPattern.containsMatchIn(currentNode)) {
            return steps
        }
        return traverse(
            if (stepSequence[steps.mod(stepSequence.length)] == 'L') terrain[currentNode]!!.first
            else terrain[currentNode]!!.second
        , endPattern, steps + 1)
    }
}