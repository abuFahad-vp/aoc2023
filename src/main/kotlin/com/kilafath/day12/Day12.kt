package com.kilafath.day12
import com.kilafath.*

class Day12 {

    private val cache = mutableMapOf<Pair<String,List<Int>>, Long>()

    init { readAsLines("day12/input.txt").solve() }

    private fun List<String>.solve() {
        val (part1,part2) = this.fold(Pair(0L,0L)) {acc, line ->
            val (seq,recordsStr) = line.split(' ')
            val records = recordsStr.split(',').map { it.toInt() }
            Pair(acc.first + countValidCombinations(seq, records),
                acc.second + countValidCombinations(List(5) {seq}.joinToString("?"),
                    List(5) {records}.flatten()))
        }
        println("part 1: $part1, part 2: $part2")
    }

    private fun countValidCombinations(seq: String, records: List<Int>): Long {
        if (cache[Pair(seq,records)] != null) return cache[Pair(seq,records)]!!
        if (records.isEmpty()) return if ('#' in seq) 0 else 1
        if (seq.isEmpty()) return if (records.isEmpty()) 1 else 0

        var total = 0L
        if (seq[0] in listOf('.', '?')) {
            total += countValidCombinations(seq.drop(1), records)
        }
        if (seq[0] in listOf('#', '?')) {
            if (isValidCondition(seq,records)) {
                total += countValidCombinations(
                    seq.drop(records[0]+1), records.drop(1))
            }
        }
        cache[Pair(seq,records)] = total
        return total
    }

    private fun isValidCondition(seq: String, records: List<Int>): Boolean {
        return (records[0] <= seq.length) &&
                '.' !in seq.slice(0..<records[0]) &&
                (records[0] == seq.length ||
                        seq[records[0]] != '#')
    }
}