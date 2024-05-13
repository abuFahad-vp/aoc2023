package com.kilafath.day03
import com.kilafath.*

class Day03 {
    init {
        readAsLines("day03/input.txt").solve()
    }

    private fun List<String>.solve() {
        val numPattern = Regex("""\d+""")
        val symbolPattern = Regex("""[^\d.]""")
        val ans = this.foldIndexed(0) { index,acc, line ->
            acc + numPattern.findAll(line).fold(0) {accInner, elem ->
                for(i in (if (index > 0) index-1 else index)..(if (index < this.size-1) index+1 else index)) {
                    val matchedSymbols = symbolPattern.findAll(this[i])
                    if(isPartNumber(matchedSymbols,elem.range)) {
                        return@fold accInner + elem.value.toInt()
                    }
                }
                accInner
            }
        }
        println(ans)
    }

    private fun isPartNumber(matchedSymbols: Sequence<MatchResult>, range: IntRange): Boolean {
        return matchedSymbols.any { it.range.first in (range.first - 1)..(range.last + 1) }
    }
}