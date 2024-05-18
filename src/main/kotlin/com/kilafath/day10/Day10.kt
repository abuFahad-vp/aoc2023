package com.kilafath.day10

import com.kilafath.readAsLines
import kotlin.math.max
import kotlin.math.min

class Day10 {
    init {
        readAsLines("day10/input.txt").solve()
    }

    private fun List<String>.solve() {
        val visitedNodes = mutableListOf<Pair<Int, Int>>()
        val stack = mutableListOf<Pair<Int,Int>>()
        this.forEachIndexed outerLoop@ { x, line -> line.forEachIndexed { y, char ->
            if (char == 'S') {
                stack.add(Pair(x, y))
                return@outerLoop
            }
        } }

        while (stack.isNotEmpty()) {
            val currentNode = stack.removeLast()
            if (currentNode !in visitedNodes) {
                visitedNodes.add(currentNode)

                for (neighbor in this.neighbours(currentNode)) {
                    if (neighbor !in visitedNodes) {
                        stack.add(neighbor)
                    }
                }
            }
        }
        val part1 = visitedNodes.size / 2
        var part2 = 0
        visitedNodes.add(visitedNodes.first())
        for (x in 0..this.size) {
            for (y in 0..this[0].length) {
                if (visitedNodes.isInside(x,y)) part2++
            }
        }
        println("part 1 = $part1, part 2 = $part2")
    }

    private fun List<String>.neighbours(node: Pair<Int, Int>): List<Pair<Int, Int>> {
        val (x,y) = node
        val grid = this
        val direction = when(this[x][y]) {
            'S' -> buildList {
                if (x > 0 && x < grid.size - 1) {
                    if (grid[x - 1][y] in listOf('|', 'F', '7')) add(Pair(x-1, y))
                    if (grid[x + 1][y] in listOf('|', 'J', 'L')) add(Pair(x+1, y))
                }
                if (y > 0 && y < grid[0].length - 1) {
                    if (grid[x][y - 1] in listOf('F', '-', 'L')) add(Pair(x,y-1))
                    if (grid[x][y + 1] in listOf('-', 'J', '7')) add(Pair(x,y+1))
                }
            }
            '|' -> listOf(Pair(x-1, y), Pair(x+1, y))
            '-' -> listOf(Pair(x, y-1), Pair(x, y+1))
            'L' -> listOf(Pair(x-1, y), Pair(x, y+1))
            'J' -> listOf(Pair(x-1, y), Pair(x, y-1))
            '7' -> listOf(Pair(x, y-1), Pair(x+1, y))
            'F' -> listOf(Pair(x, y+1), Pair(x+1, y))
            else -> emptyList()
        }
        return direction.filter {
            it.first > 0 && it.second > 0 && it.first < grid.size - 1 && it.second < grid[0].length - 1
        }
    }

    private fun MutableList<Pair<Int,Int>>.isInside(xp: Int, yp: Int): Boolean {
        if (Pair(xp,yp) in this) return false
        var count = 0
        for (i in 1..this.lastIndex) {
            val (xi, yi) = this[i-1]
            val (xj, yj) = this[i]
            if (min(yi,yj) < yp && yp <= max(yi,yj)) {
                val xIntercept = xi + ((yp - yi) / (yj - yi).toDouble()) * (xj - xi)
                if (xIntercept > xp) count++
            }
        }
        return count % 2 != 0
    }
}