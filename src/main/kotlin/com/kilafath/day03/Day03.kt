package com.kilafath.day03
import com.kilafath.*

class Day03 {
    init {
        readAsLines("day03/input.txt").solve()

    }

    private fun isPartNumber(matchedSymbols: Sequence<MatchResult>, range: IntRange): Boolean {
        return matchedSymbols.any { it.range.first in (range.first - 1)..(range.last + 1) }
    }

    private fun List<String>.solve() {
        val numPattern = Regex("""\d+""")
        val symbolPattern = Regex("""[^\d.]""")
        val gearPatten = Regex("""\*""")
        val ans = this.foldIndexed(Pair(0,0)) { index,acc, line ->
            val part1 = acc.first + numPattern.findAll(line).fold(0) {accInner, elem ->
                for(i in (if (index > 0) index-1 else index)..(if (index < this.size-1) index+1 else index)) {
                    val matchedSymbols = symbolPattern.findAll(this[i])
                    if(isPartNumber(matchedSymbols,elem.range)) {
                        return@fold accInner + elem.value.toInt()
                    }
                }
                accInner
            }

            val part2 = acc.second + gearPatten.findAll(line).fold(0) { accInner, gear ->
                    var count = 0
                    var values = 1
                    for(i in (if (index > 0) index-1 else index)..(if (index < this.size-1) index+1 else index)) {
                        val matchedSymbols = numPattern.findAll(this[i])
                        for(elem in matchedSymbols) {
                            if (gear.range.first >= elem.range.first - 1 && gear.range.first <= elem.range.last+1) {
                                count++
                                values *= elem.value.toInt()
                            }
                        }
                    }
                    if (count == 2) accInner + values else accInner
                }
                Pair(part1, part2)
            }
        println("part 1 = ${ans.first}, part 2 = ${ans.second}")
    }
}