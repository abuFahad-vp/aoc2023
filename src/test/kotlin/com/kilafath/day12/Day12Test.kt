package com.kilafath.day12

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day12Test {

    private val day12: Day12 = Day12()

    @Test
    fun checkingValidityOfSeq() {
        val testSample = """#.#.### 1,1,3
.#...#....###. 1,1,3
.#.###.#.###### 1,3,1,6
####.#...#... 4,1,1
#....######..#####. 1,6,5
.###.##....# 3,2,1"""
        for (line in testSample.split("\n")) {
            val (seq,recordsStr) = line.split(' ')
            val records = recordsStr.split(',')
            println("$seq, $records")
            assertEquals(true, day12.isValidSeq(seq, records))
            println("OK: $seq, $records")
        }
    }
}