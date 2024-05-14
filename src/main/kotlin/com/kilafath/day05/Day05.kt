package com.kilafath.day05
import com.kilafath.*
import kotlin.math.min

class Day05 {
    private val input = readAsString("day05/input.txt")
    init { input.solve() }

    private fun String.solve() {
        val lines = this.split("\n\r")
        val seeds = Regex("""\d+""").findAll(lines[0]).map { it.value.toLong() }.toList()
        var f: (Long) -> Long = {it}
        for(i in 1..lines.lastIndex) f = f then parseBlock(lines[i])
        val part1 = seeds.fold(Long.MAX_VALUE) {acc, s -> min(acc, f(s))}
        var part2 = Long.MAX_VALUE
        for(i in 0..seeds.lastIndex-2 step 2) {
            for(seed in seeds[i]..(seeds[i] + seeds[i + 1])) {
                part2 = min(part2,f(seed))
            }
        }
        println("part 1 = $part1, part 2 = $part2")
    }
    private fun parseBlock(block: String):(Long) -> Long {
        val cnstrn = List(3) { index -> block.split("\n").drop(2).fold(listOf<Long>()) { acc, line ->
                acc + Regex("""\d+""").findAll(line).map { it.value.toLong() }.toList()
            }.filterIndexed { i, _ -> i % 3 == index }
        }
        return {s ->
            var mapped = s
            val dest = cnstrn[0]; val src = cnstrn[1]; val step = cnstrn[2]
            dest.forEachIndexed { i, destx -> if (s >= src[i] && s < src[i]+step[i]) mapped = destx + s - src[i] }
            mapped
        }
    }
}