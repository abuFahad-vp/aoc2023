package com.kilafath.day04

import com.kilafath.*
import kotlin.math.floor
import kotlin.math.pow

class Day04 {
    init {
        readAsLines("day04/input.txt").solve()
    }
    private fun List<String>.solve() {
        val winningCards = Regex("""\d+(?=\s)(?=.*\|)""")
        val availableCards = Regex("""(?<=\|.{1,100})(\d+)""")
        val matchedCards = ArrayList<Int>()
        val result = this.fold(Pair(0.0,matchedCards)) { acc, line ->
            val winCards = winningCards.findAll(line).map { it.value }.toSet()
            val availCards = availableCards.findAll(line).map { it.value }.toSet()
            val matchedNum = winCards.intersect(availCards).size
            matchedCards.add(matchedNum)
            Pair(acc.first + floor(2.0.pow(matchedNum-1)),matchedCards)
        }
        val compoundCards = IntArray(this.size) { 1 }
        val part2 = result.second.foldIndexed(0) {index, acc, iter ->
            val toAdd = compoundCards[index]
            (index+1..index+iter).forEach {compoundCards[it] += toAdd }
            acc + compoundCards[index]
        }
        println("part 1 = ${result.first.toInt()}, part 2 =  $part2")
    }
}