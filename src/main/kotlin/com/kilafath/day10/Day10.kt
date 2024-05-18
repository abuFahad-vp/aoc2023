package com.kilafath.day10

import com.kilafath.readAsLines

class Day10 {
    init {
        readAsLines("day10/input.txt").solve()
    }

    private fun List<String>.solve() {
        val visitedNodes = mutableSetOf<Pair<Int, Int>>()
        val stack = mutableListOf<Pair<Int,Int>>()
        this.forEachIndexed outerLoop@ { x, line -> line.forEachIndexed { y, char ->
            if (char == 'S') {
                stack.add(Pair(x, y))
                return@outerLoop
            }
        } }

        var steps = 0
        while (stack.isNotEmpty()) {
            val currentNode = stack.removeLast()
            if (currentNode !in visitedNodes) {
                steps += 1
                visitedNodes.add(currentNode)

                for (neighbor in this.neighbours1(currentNode)) {
                    if (neighbor !in visitedNodes) {
                        stack.add(neighbor)
                    }
                }
            }
        }
        println("part 1 = ${steps / 2}")
    }

    private fun List<String>.neighbours1(node: Pair<Int, Int>): List<Pair<Int, Int>> {
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
}