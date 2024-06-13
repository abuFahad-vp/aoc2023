package com.kilafath.day15

import com.kilafath.*

class Day15 {
    init {
        val input = readAsString("day15/input.txt").split(",")
        val hashAlgo = {str: String -> str.fold(0) {acc, c -> ((acc + c.code) * 17) % 256}}
        val lightChain = List(256) {LinkedHashMap<String,Int>()}
        input.forEach {
            if (it.contains('=')) {
                val (label, fLen) = it.split("=")
                lightChain[hashAlgo(label)][label] = fLen.toInt()
            }else {
                val (label, _) = it.split("-")
                lightChain[hashAlgo(label)].remove(label)
            }
        }
        val part1 = input.fold(0) {acc,code -> acc + hashAlgo(code)}
        val part2 = lightChain.foldIndexed(0) {index, acc, box ->
            val boxV =
                box.keys.toList().foldIndexed(0){idx, v, lens -> v + (idx + 1) * (lightChain[index][lens]?:0)}
            acc + (index + 1) * boxV
        }
        println("part 1: $part1, part 2: $part2")
    }
}