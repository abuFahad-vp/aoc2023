package com.kilafath.day12

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day12Test {

    private val day12: Day12 = Day12()

    @Test
    fun checkingValidityOfSeq() {
        val testSample = """???.### 1,1,3
.??..??...?##. 1,1,3
?#?#?#?#?#?#?#? 1,3,1,6
????.#...#... 4,1,1
????.######..#####. 1,6,5
?###???????? 3,2,1"""
        val (part1,part2) = testSample.split("\n").fold(Pair(0L,0L)) {acc, line ->
            val (seq,recordsStr) = line.split(' ')
            val records = recordsStr.split(',').map { it.toInt() }
            Pair(acc.first + day12.countValidCombinations(seq, records),
                acc.second + day12.countValidCombinations(List(5) {seq}.joinToString("?"),
                    List(5) {records}.flatten()))
        }
        assertEquals(21, part1)
        assertEquals(525152, part2)
    }
}