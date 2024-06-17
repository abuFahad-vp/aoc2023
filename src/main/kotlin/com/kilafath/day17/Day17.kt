package com.kilafath.day17

import com.kilafath.readAsLines
import java.util.PriorityQueue

class Day17 {
    private val grid = readAsLines("day17/input.txt").map {it.map {c -> c.code - '0'.code }}
    data class Node(var hl: Int, var r: Int, var c: Int, var dr: Int, var dc: Int,var n: Int)

    init {
        println("part 1 = ${shortPathPart1()}")
        println("part 2 = ${shortPathPart2()}")
    }
    private fun shortPathPart2(): Int {
        val pq = PriorityQueue<Node>(compareBy {it.hl})
        val seen = mutableSetOf<Node>()
        pq.add(Node(0,0,0,0,0,0))
        while(pq.isNotEmpty()) {
            val (hl,r,c,dr,dc,n) = pq.remove()
            if (r == grid.size - 1 && c == grid[0].size - 1 && n >= 4) {
                return hl
            }
            if (Node(0,r,c,dr,dc,n) in seen) {
                continue
            }
            seen.add(Node(0,r,c,dr,dc,n))
            if (n < 10 && Pair(dr,dc) != Pair(0,0)) {
                goForward(pq,Node(hl,r,c,dr,dc,n))
            }
            if (n >= 4 || Pair(dr,dc) == Pair(0,0)) {
                turn(pq,Node(hl,r,c,dr,dc,n))
            }
        }
        return 0
    }
    private fun shortPathPart1(): Int {
        val pq = PriorityQueue<Node>(compareBy {it.hl})
        val seen = mutableSetOf<Node>()
        pq.add(Node(0,0,0,0,0,0))
        while(pq.isNotEmpty()) {
            val (hl,r,c,dr,dc,n) = pq.remove()
            if (r == grid.size - 1 && c == grid[0].size - 1) {
                return hl
            }
            if (Node(0,r,c,dr,dc,n) in seen) {
                continue
            }
            seen.add(Node(0,r,c,dr,dc,n))
            if (n < 3 && Pair(dr,dc) != Pair(0,0)) {
                goForward(pq,Node(hl,r,c,dr,dc,n))
            }
            turn(pq,Node(hl,r,c,dr,dc,n))
        }
        return 0
    }
    private fun goForward(pq: PriorityQueue<Node>, node: Node) {
        val (hl,r,c,dr,dc,n) = node
        val nr = r + dr
        val nc = c + dc
        if (0 <= nr && nr < grid.size && 0 <= nc && nc < grid[0].size) {
            pq.add(Node(hl + grid[nr][nc], nr, nc, dr, dc, n+1))
        }
    }
    private fun turn(pq: PriorityQueue<Node>, node: Node) {
        val (hl,r,c,dr,dc,_) = node
        for ((ndr, ndc) in listOf(Pair(0, 1), Pair(1, 0), Pair(0, -1), Pair(-1, 0))) {
            if (Pair(ndr,ndc) != Pair(dr,dc) && Pair(ndr,ndc) != Pair(-dr,-dc)) {
                val nr = r + ndr
                val nc = c + ndc
                if (0 <= nr && nr < grid.size && 0 <= nc && nc < grid[0].size) {
                    pq.add(Node(hl + grid[nr][nc], nr, nc, ndr, ndc, 1))
                }
            }
        }
    }
}