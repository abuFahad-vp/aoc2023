package com.kilafath.day05
import com.kilafath.*
import kotlin.math.min

class Day05 {
    private val input = readAsString("day05/input.txt")

    init {
        part1(input)
        part2(input)
    }

    private fun part1(input: String) {
        val lines = input.split("\n\r")
        val seeds = Regex("""\d+""").findAll(lines[0]).map { it.value.toLong() }.toList()
        var f: (Long) -> Long = { it }
        for (i in 1..lines.lastIndex) f = f then parseBlock(lines[i])

        val part1 = seeds.fold(Long.MAX_VALUE) { acc, s -> min(acc, f(s)) }
        println("part 1 = $part1")
    }

    private fun part2(input: String) {
        val lines = input.split("\n\r")
        var rangeLimit = Regex("""\d+""").findAll(lines[0]).map { it.value.toLong() }
            .toList()
            .chunked(2) {
                listOf(it[0], it[0] + it[1] - 1)
            }
        for (i in 1..lines.lastIndex) {
            val mapRanges = mutableMapOf<List<Long>, List<Long>>()
            lines[i].split("\n").drop(2).forEach { line ->
                val ranges = Regex("""\d+""").findAll(line).map { it.value.toLong() }
                val step = ranges.elementAt(2)
                val srcF = ranges.elementAt(1)
                val srcL = srcF + step - 1
                val destF = ranges.elementAt(0)
                val destL = destF + step - 1
                mapRanges[listOf(srcF, srcL)] = listOf(destF, destL)
            }
            rangeLimit = generateRanges(mapRanges, rangeLimit)
        }
        println("part 2 = ${rangeLimit.minOf { it[0] }}")
    }

    private fun parseBlock(block: String): (Long) -> Long {
        val cnstrn = List(3) { index ->
            block.split("\n").drop(2).fold(listOf<Long>()) { acc, line ->
                acc + Regex("""\d+""").findAll(line).map { it.value.toLong() }.toList()
            }.filterIndexed { i, _ -> i % 3 == index }
        }
        return { s ->
            var mapped = s
            val dest = cnstrn[0]
            val src = cnstrn[1]
            val step = cnstrn[2]
            dest.forEachIndexed { i, destx -> if (s >= src[i] && s < src[i] + step[i]) mapped = destx + s - src[i] }
            mapped
        }
    }

    private fun generateRanges(
        input: MutableMap<List<Long>, List<Long>>,
        rangeLimit: List<List<Long>>
    ): MutableList<List<Long>> {
        val sortedRanges = input.keys.sortedBy { it[0] }
        val rangeLimitNew = mutableListOf<List<Long>>()
        for (limits in rangeLimit) {
            var current = limits[0]
            val seedEnd = limits[1]
            for (range in sortedRanges) {
                val start = range[0]
                val end = range[1]
                if (seedEnd <= end) {
                    if (seedEnd < start) {
                        rangeLimitNew.add(listOf(current, seedEnd))
                        break
                    }
                    if (current < start) {
                        rangeLimitNew.add(listOf(current, start - 1))
                        val destStart = input[listOf(start, end)]!![0]
                        val destEnd = destStart + (seedEnd - start)
                        rangeLimitNew.add(listOf(destStart, destEnd))
                        break
                    }
                    val lowerLimit = input[listOf(start, end)]!![0]
                    val destStart = lowerLimit + (current - start)
                    val destEnd = lowerLimit + (seedEnd - start)
                    rangeLimitNew.add(listOf(destStart, destEnd))
                    break
                } else if (current in start..end) {
                    val lowerLimit = input[listOf(start, end)]
                    val destStart = lowerLimit!![0] + (current - start)
                    rangeLimitNew.add(listOf(destStart, lowerLimit[1]))
                    current = end + 1
                } else if (current < start) {
                    val upperLimit = input[listOf(start, end)]
                    rangeLimitNew.add(listOf(current, start - 1))
                    rangeLimitNew.add(listOf(upperLimit!![0], upperLimit[1]))
                    current = end + 1
                }
            }
            if (sortedRanges.last()[1] < seedEnd) {
                rangeLimitNew.add(listOf(current, seedEnd))
            }
        }
        return rangeLimitNew
    }
}