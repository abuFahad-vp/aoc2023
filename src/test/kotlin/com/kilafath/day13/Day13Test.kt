package com.kilafath.day13

import kotlin.test.Test
import kotlin.test.assertEquals

internal class Day13Test {

    private val day13: Day13 = Day13()

    @Test
    fun `find the mirror and it's value`() {
        val sample1 = """#.##..##.
..#.##.#.
##......#
##......#
..#.##.#.
..##..##.
#.#.##.#."""
        val sample2 = """#...##..#
#....#..#
..##..###
#####.##.
#####.##.
..##..###
#....#..#"""
        val sample3 = """..####.
#..#.##
.###.#.
#.##..#
###...#
.#.#...
.#.#...
###...#
#.##..#
.###.#.
#..####
..####."""
//        assertEquals(Pair(-1,-1),day13.findMirrorOnRow(sample1))
//        assertEquals(Pair(3,4),day13.findMirrorOnRow(sample2))
//        assertEquals(Pair(4,5),day13.findMirrorOnCol(sample1))
//        assertEquals(Pair(-1,-1),day13.findMirrorOnCol(sample2))
//        assertEquals(5,day13.blockValue(sample1))
//        assertEquals(400,day13.blockValue(sample2))
//        println(day13.findMirrorOnRow(sample3))
//        println(day13.findMirrorOnCol(sample3))
//        println(day13.blockValue(sample3))
    }
}
