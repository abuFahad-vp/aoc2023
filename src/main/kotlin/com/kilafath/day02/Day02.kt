package com.kilafath.day02
import com.kilafath.*

class Day02 {
    init {
        readAsLines("day02/input.txt").solve()
    }

    private fun List<String>.solve() {
//        only 12 red cubes, 13 green cubes, and 14 blue
        val ans =  this.foldIndexed(Pair(0,0)) {i,acc,line ->
            val red = line.maxValue("red")
            val green = line.maxValue("green")
            val blue = line.maxValue("blue")
            Pair(if (red <= 12 && green <= 13 && blue <= 14) acc.first + i + 1 else acc.first,
            acc.second + red * green * blue)
        }
        println("part 1 = ${ans.first}, part 2 = ${ans.second}")
    }

    private fun String.maxValue(match: String):Int {
        val pattern = Regex("""\b([^ \n]+)\b(?=\s*$match)""")
        val matches = pattern.findAll(this)
        return matches.fold(Int.MIN_VALUE) {maxV, elem ->
            val elemV = elem.groupValues[0].toInt()
            if (maxV < elemV) elemV else maxV
        }
    }
}