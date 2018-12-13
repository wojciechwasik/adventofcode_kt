package aoc2018.day2

import util.InputReader
import util.compareAll

//
// found match: nmgyjkpruszlbaqwficavxneo
//

fun main(args: Array<String>) {
    val input = InputReader("/day2.txt").readLines()
    val length = input[0].length - 1
    compareAll(input, ::extractMatch)
        .filter { it.length == length }
        .forEach { println("found match: $it") }
}

private fun extractMatch(l: String, r:String) = String(l.toCharArray().filterIndexed { index, c -> c == r[index] }.toCharArray())
