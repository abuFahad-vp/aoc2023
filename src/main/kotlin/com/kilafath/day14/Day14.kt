package com.kilafath.day14

import com.kilafath.*
import kotlin.math.sqrt

class Day14 {
    init {
        val input = readAsLines("day14/sample.txt").joinToString("").toMutableList()
        tilt(input,'n')
        tilt(input,'w')
        tilt(input,'s')
        tilt(input,'e')
        printGridValue(input)
        println(valueOfDish(input))
    }

    private fun tilt(arr: MutableList<Char>, direction: Char) {
//        for (idx in 0 until sizeT) {
//            for (i in idx..arr.lastIndex - 2 * sizeT step sizeT) {
//                for (j in idx..arr.lastIndex-sizeT step sizeT) {
//        for (idx in 0..<arr.size step sizeT ) {
//            for (i in idx..idx+sizeT-3) {
//                for (j in idx..idx+sizeT-2) {
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

    private fun tiltWestEast(arr: MutableList<Char>,isWest:Boolean = false) {
        val compare = if (isWest) {
            {a: Char, b: Char -> a == 'O' && b == '.'}
        }else {
            {a: Char, b: Char -> a == '.' && b == 'O'}
        }
        val sizeT = sqrt(arr.size.toDouble()).toInt()
        for (idx in 0..<arr.size step sizeT ) {
            for (i in idx..idx+sizeT-2) {
                for (j in idx..idx+sizeT-2) {
                    if (compare(arr[j],arr[j + 1])) {
                        val temp = arr[j+1]
                        arr[j+1] = arr[j]
                        arr[j] = temp
                    }
                }
            }
        }
    }

    private fun tiltNorthSouth(arr: MutableList<Char>,isSouth: Boolean = false) {
        val sizeT = sqrt(arr.size.toDouble()).toInt()
        val compare = if (isSouth) {
            {a: Char, b: Char -> a == 'O' && b == '.'}
        }else {
            {a: Char, b: Char -> a == '.' && b == 'O'}
        }
        for (idx in 0 until sizeT) {
            for (i in idx..arr.lastIndex - 2 * sizeT step sizeT) {
                for (j in idx..arr.lastIndex-sizeT step sizeT) {
                    if (compare(arr[j], arr[j+sizeT])) {
                        val temp = arr[j+sizeT]
                        arr[j+sizeT] = arr[j]
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

    private fun printGridValue(input: MutableList<Char>) {
        val sizeT = sqrt(input.size.toDouble()).toInt()
        for (i in input.indices) {
            print("${input[i]} ")
            if (i % sizeT == sizeT - 1) println()
        }
    }
}