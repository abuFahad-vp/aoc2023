package com.kilafath.day09

import com.kilafath.readAsLines

class Day09 {
    init {
        val (part1, part2) = readAsLines("day09/input.txt").fold(Pair<Long,Long>(0,0)) { acc, line ->
            Pair(0,0)
            val list = Regex("""-?\d+""").findAll(line).map { x -> x.value.toLong() }.toList()
            Pair(list.nextTerm() + acc.first,
                list.reversed().nextTerm() + acc.second)
        }
        println("part1: $part1, part2: $part2")
    }

    private tailrec fun List<Long>.nextTerm(seq: List<Long> = this, current: Long = 0): Long {
        return if (seq.all { it == 0L }) current
         else nextTerm(seq.zipWithNext {a,b -> b - a}, current + seq.last())
    }
}