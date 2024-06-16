package com.kilafath.day14

import com.kilafath.*
import kotlin.math.sqrt

class Day14 {
    init {
        val input = readAsLines("day14/input.txt").joinToString("").toMutableList()
        val cache = mutableListOf(mutableListOf<Char>())
        val gridValues = mutableListOf<Long>()
        val cycles = 1000000000
        var firstCycle = -1
        var cycleGrid: MutableList<Char> = mutableListOf()
        for (i in 0..cycles) {
            tilt(input,'n')
            if(i == 0) println("part 1 = ${valueOfDish(input)}")
            tilt(input,'w')
            tilt(input,'s')
            tilt(input,'e')
            if (input == cycleGrid) {
                val index = (cycles - (firstCycle-1)) % (i - firstCycle) + firstCycle - 2
                println("part 2 = ${gridValues[index]}")
                break
            }
            if (firstCycle == -1 && input in cache) {
                cycleGrid = input.toMutableList()
                firstCycle = i
            }
            cache.add(input.toMutableList())
            gridValues.add(valueOfDish(input))
        }
    }

    private fun tilt(arr: MutableList<Char>, direction: Char) {
        val sizeT = sqrt(arr.size.toDouble()).toInt()
        val rangeIdx:IntProgression
        val rangeInner:(idx: Int) -> IntProgression
        val step: Int
        if (direction == 'n' || direction == 's') {
            rangeIdx = 0 until sizeT
            rangeInner = {idx -> idx..arr.lastIndex-sizeT step sizeT}
            step = sizeT
        }else{
            rangeIdx = 0..<arr.size step sizeT
            rangeInner = {idx -> (idx..idx+sizeT-2)}
            step = 1
        }
        val compare = if (direction == 'n' || direction == 'w') {
            {a: Char, b: Char -> a == '.' && b == 'O'}
        }else {
            {a: Char, b: Char -> a == 'O' && b == '.'}
        }
        for (idx in rangeIdx) {
            for (i in rangeInner(idx)) {
                for (j in rangeInner(idx)) {
                    if (compare(arr[j],arr[j + step])) {
                        val temp = arr[j+step]
                        arr[j+step] = arr[j]
                        arr[j] = temp
                    }
                }
            }
        }
    }

    private fun valueOfDish(arr: MutableList<Char>): Long {
        val sizeT = sqrt(arr.size.toDouble()).toInt()
        var value = 0L
        var sum = 0L
        for (i in arr.indices) {
            if (arr[i] == 'O') value += 1
            if (i % sizeT == sizeT - 1) {
                sum += value * (sizeT - (i / sizeT))
                value = 0
            }
        }
        return sum
    }

}