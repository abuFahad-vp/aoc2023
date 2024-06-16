package com.kilafath.day16
import com.kilafath.*
import kotlin.math.max

class Day16 {
    data class Beam(var r: Int, var c: Int, var dr: Int, var dc: Int)
    private val grid = readAsLines("day16/input.txt")
    init {
        println(grid)
        val part1 = energized(Beam(0,-1,0,1))
        var part2 = 0
        for (row in 0..grid.size) {
            part2 = max(part2, energized(Beam(row,-1,0,1)))
            part2 = max(part2, energized(Beam(row,grid[0].length,0,-1)))
        }
        for (col in 0..grid[0].length) {
            part2 = max(part2, energized(Beam(-1,col,1,0)))
            part2 = max(part2, energized(Beam(grid.size,col,-1,0)))
        }

        println("part 1: $part1, part 2: $part2")
    }

    private fun energized(beam: Beam): Int {
        val q = mutableListOf(beam)
        val seen = mutableSetOf<Beam>()

        while (q.isNotEmpty()) {
            var (r,c,dr,dc) = q.removeFirst()
            r += dr
            c += dc
            if (r < 0 || r >= grid.size || c < 0 || c >= grid[0].length) {
                continue
            }

            val ch = grid[r][c]

            if (ch == '.' || (ch == '-' && dc != 0) || (ch == '|' && dr != 0)) {
                if (Beam(r,c,dr,dc) !in seen) {
                    seen.add(Beam(r,c,dr,dc))
                    q.add(Beam(r,c,dr,dc))
                }
            }else if (ch == '/') {
                dr = -dc.also {dc = -dr}
                if (Beam(r,c,dr,dc) !in seen) {
                    seen.add(Beam(r,c,dr,dc))
                    q.add(Beam(r,c,dr,dc))
                }
            }else if (ch == '\\') {
                dr = dc.also {dc = dr}
                if (Beam(r,c,dr,dc) !in seen) {
                    seen.add(Beam(r,c,dr,dc))
                    q.add(Beam(r,c,dr,dc))
                }
            }else {
                val directions = if (ch == '|') {
                    listOf(Pair(1,0), Pair(-1,0))
                }else {
                    listOf(Pair(0,1), Pair(0,-1))
                }
                for (direction in directions) {
                    dr = direction.first
                    dc = direction.second
                    if (Beam(r,c,dr,dc) !in seen) {
                        seen.add(Beam(r,c,dr,dc))
                        q.add(Beam(r,c,dr,dc))
                    }
                }
            }
        }
        return seen.map {Pair(it.r, it.c)}.toSet().size
    }
}