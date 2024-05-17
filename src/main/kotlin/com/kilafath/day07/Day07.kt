package com.kilafath.day07

import kotlin.math.min
import com.kilafath.*

class Day07 {
    init {
        readAsLines("day07/input.txt").solve()
    }
    private fun List<String>.solve() {
        val game = mutableMapOf<String, Int>()
        this.forEach { val cardAndBid = it.split(" ")
            game[cardAndBid[0]] = cardAndBid[1].toInt()
        }
        val part1 = game.keys.sortHeads(false).foldIndexed(0) { index, acc, hand ->
            acc + game[hand]?.times((index + 1))!!
        }
        val part2 = game.keys.sortHeads(true).foldIndexed(0) { index, acc, hand ->
            acc + game[hand]?.times((index + 1))!!
        }
        println("part1: $part1, part2: $part2")
    }


    private fun Set<String>.sortHeads(isJoker: Boolean): List<String> {
        val sortAns = this.sortedWith(HandComparator(isJoker))
        return sortAns
    }

    class HandComparator(private val isJoker: Boolean) : Comparator<String> {
        override fun compare(str1: String, str2: String): Int {
            val handValue1 = str1.countCharacters()
            val cardValue1 = str1.cardValue()

            val handValue2 = str2.countCharacters()
            val cardValue2 = str2.cardValue()

            val sortValue = compareList(handValue1,handValue2)
            return if (sortValue == 0) {
                compareList(cardValue1,cardValue2)
            }else {
                sortValue
            }
        }

        private fun compareList(l1: List<Int>, l2: List<Int>): Int {
            for(i in 0..min(l1.lastIndex,l2.lastIndex)) {
                if (l1[i] > l2[i]) {
                    return 1
                }else if(l2[i] > l1[i]) {
                    return -1
                }
            }
            return 0
        }

        private fun String.countCharacters(): List<Int> {
            val charCount = mutableMapOf<Char, Int>()
            for (char in this) charCount[char] = charCount.getOrDefault(char, 0) + 1
            if (isJoker && 'J' in this) {
                val jCount = charCount['J']!!
                if (jCount < 5) {
                    charCount.remove('J')
                    val cardOccurence = charCount.values.sortedDescending().toMutableList()
                    cardOccurence[0] += jCount
                    return cardOccurence
                }
            }
            return charCount.values.sortedDescending()
        }

        private fun String.cardValue(): List<Int> {
            val cardIndex = if(!isJoker) "23456789TJQKA" else "J23456789TQKA"
            return this.fold(listOf()) {acc, char ->
                acc + cardIndex.indexOf(char)
            }
        }
    }
}